package guru.qa.niffler.jupiter.category;

import guru.qa.niffler.api.category.CategoryApiClient;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.util.DataHelper;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

public class CategoryExtension implements
        BeforeEachCallback,
        ParameterResolver,
        AfterEachCallback
{

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace
            .create(CategoryExtension.class);
    private final CategoryApiClient categoryApiClient = new CategoryApiClient();


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), Category.class)
                .ifPresent(
                        anno -> {
                            CategoryJson categoryJson = new CategoryJson(
                                    null,
                                    DataHelper.getRandomCategory(),
                                    anno.username(),
                                    false
                            );
                            CategoryJson created = categoryApiClient.createCategory(categoryJson);
                            if (anno.archived()) {
                                CategoryJson archivedCategory = new CategoryJson(
                                        created.id(),
                                        created.name(),
                                        created.username(),
                                        true
                                );
                                created = categoryApiClient.updateCategory(archivedCategory);
                            }
                            context.getStore(NAMESPACE).put(
                                    context.getUniqueId(),
                                    created);
                        }
                );
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(CategoryJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(
                extensionContext.getUniqueId(),
                CategoryJson.class
        );
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        CategoryJson category =
                context.getStore(NAMESPACE).get(context.getUniqueId(), CategoryJson.class);
        if (!category.archived()) {
            CategoryJson archivedCategory = new CategoryJson(
                    category.id(),
                    category.name(),
                    category.username(),
                    true
            );
            categoryApiClient.updateCategory(archivedCategory);
        }
    }
}
