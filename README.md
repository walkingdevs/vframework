#### Установка
    
pom.xml:

    <dependency>
        <groupId>brains.vframework</groupId>
        <artifactId>vframework</artifactId>
        <version>2.1</version>
    </dependency>

web.xml:

    <context-param>
        <param-name>basePackage</param-name>
        <param-value>kz.taimax</param-value>
    </context-param>

    <context-param>
        <param-name>vframeworkContext</param-name>
        <param-value>web</param-value>
    </context-param>

    <context-param>
        <param-name>theme</param-name>
        <param-value>brown</param-value>
    </context-param>
    
Значения по умолчанию:

    basePackage         = Нет! Обязательный параметр
    vframeworkContext   = vframework
    theme               = brown
    
UI по умолчанию (ака index.html)

    @DefaultUI
    public class HelloWorldUI extends VUI {
    
        @Override
        protected void init(VaadinRequest vaadinRequest) {
            setContent(new Label("Hello, world!"));
        }
    }
    
Локализация
    
    mkdir path/to/WEB-INF/locale
    cd path/to/WEB-INF/locale
    touch messages_en.properties messages_ru.properties messages_kz.properties 
    
    VMessagesContext.getMessage("hi");
    