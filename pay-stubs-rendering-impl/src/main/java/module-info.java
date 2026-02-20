module com.guerrer0jaguar.paystubs.rendering.impl {
       
    requires com.guerrer0jaguar.paystubs.entity;
    requires com.guerrer0jaguar.paystubs.rendering;
    requires com.github.librepdf.openpdf;
    requires java.desktop;
    
    provides com.guerrer0jaguar.paystubs.rendering.PayStubRendering 
        with com.guerrer0jaguar.paystubs.rendering.impl.PayStubRenderingPDFimpl;

    exports com.guerrer0jaguar.paystubs.rendering.impl;
}
