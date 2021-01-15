/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.kupa.xmlbench;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

//@BenchmarkMode(Mode.All)
//@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
//@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
//@Fork(3)
//@BenchmarkMode(Mode.AverageTime)
//@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class XmlBenchmark {

	DocumentBuilderFactory factInstance = createDocumentBuilderFactory();
	DocumentBuilder builder = createBuilder(factInstance);

	@Benchmark
	@Fork(value = 1, warmups = 1)
	public void getXmlDocBuilder(Blackhole blackhole) throws Exception {
		blackhole.consume(createDoc("factDoc", createDocumentBuilderFactory()));
	}
	
	@Benchmark
	@Fork(value = 1, warmups = 1,jvmArgs = "-Djavax.xml.parsers.DocumentBuilderFactory=com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl")
	public void getXmlDocBuilderParam(Blackhole blackhole) throws Exception {
		blackhole.consume(createDoc("factDoc", createDocumentBuilderFactory()));
	}

	@Benchmark
	@Fork(value = 2, warmups = 1)
	public void getXmlDocBuilderReuseFactory(Blackhole blackhole) throws Exception {
		blackhole.consume(createDoc("factDoc", factInstance));
	}

	@Benchmark
	@Fork(value = 2, warmups = 1)
	public void getXmlDocBuilderReuseBuilder(Blackhole blackhole) throws Exception {
		blackhole.consume(createDoc("factDoc", builder));
	}

	public Document createDoc(String root_name, DocumentBuilderFactory factory) throws ParserConfigurationException {
		Document xmldoc = null;
		DocumentBuilder builder=createBuilder(factory);
		DOMImplementation impl = builder.getDOMImplementation();
		xmldoc = impl.createDocument(null, root_name, null);
		return xmldoc;
	}

	public Document createDoc(String root_name, DocumentBuilder builder) throws ParserConfigurationException {
		Document xmldoc = null;
		DOMImplementation impl = builder.getDOMImplementation();
		xmldoc = impl.createDocument(null, root_name, null);
		return xmldoc;
	}

	public static DocumentBuilderFactory createDocumentBuilderFactory() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, Boolean.TRUE);
		} catch (ParserConfigurationException e) {
			throw new IllegalStateException(e);
		}

		return factory;
	}

	public DocumentBuilder createBuilder(DocumentBuilderFactory fact) {
		try {
			return fact.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new IllegalStateException(e);
		}
	}
	


}
