package org.kupa.xmlbench;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

class XmlBenchmarkTest {

	private XmlBenchmark xmlBench = new XmlBenchmark();
	
	@Test
	void testCreateDocStringDocumentBuilder() throws Exception {

		DocumentBuilderFactory fact = XmlBenchmark.createDocumentBuilderFactory();
	    DocumentBuilder builder = xmlBench.createBuilder(fact);
	    Document doc = xmlBench.createDoc("DoC", builder);
	}

}
