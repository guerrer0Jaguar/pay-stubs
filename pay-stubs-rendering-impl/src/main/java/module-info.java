module com.guerrer0jaguar.paystubs.rendering.impl {
       
    requires transitive com.guerrer0jaguar.paystubs.entity;
    requires transitive com.guerrer0jaguar.paystubs.rendering;
    requires transitive com.github.librepdf.openpdf;    
    
    provides com.guerrer0jaguar.paystubs.rendering.PayStubRendering 
        with com.guerrer0jaguar.paystubs.rendering.impl.PayStubRenderingPDFimpl;

    exports com.guerrer0jaguar.paystubs.rendering.impl;
}
