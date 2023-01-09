package choice.university.ivan.soapapi.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchema;

@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
public class AppConfigTest {
    @Autowired
    private ServletRegistrationBean<MessageDispatcherServlet> servletRegistrationBean;
    @Autowired
    private DefaultWsdl11Definition defaultWsdl11Definition;
    @Autowired
    private XsdSchema xsdSchema;
    @Autowired
    SoapFaultMappingExceptionResolver soapFaultMappingExceptionResolver;

    @Test
    void testContextLoads() {
        assertNotNull(servletRegistrationBean);
        assertNotNull(defaultWsdl11Definition);
        assertNotNull(xsdSchema);
        assertNotNull(soapFaultMappingExceptionResolver);
    }
}
