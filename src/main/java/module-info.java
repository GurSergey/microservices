module postcard.order.portal {
    opens net.dol.postcard.order.portal;//class org.springframework.boot.devtools.restart.RestartLauncher cannot access class net.dol.postcard.order.portal.OrderPortalApp



    //compile dependencies
    requires spring.boot;
    requires spring.boot.autoconfigure;



    //spring runtime introspection dependencies
    requires spring.context;
    requires spring.core;
    requires spring.beans;



    requires jdk.unsupported;//for netty low-level API access
}