package com.sysbuddy.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * A simple XML parser which uses the internal {@link org.xml.sax} API to
 * create a tree of {@link Node} objects.
 * 
 * Credits to Graham Edgecombe
 * 
 * @author Graham
 */
public final class XmlParser implements NodeParser {

	/**
	 * A class which handles SAX events.
	 * @author Graham
	 */
	private final class XmlHandler extends DefaultHandler {

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			Node next = new Node(localName);

			if (rootNode == null) {
				rootNode = currentNode = next;
			} else {
				currentNode.addChild(next);
				nodeStack.add(currentNode);
				currentNode = next;
			}

			if (attributes != null) {
				int attributeCount = attributes.getLength();
				for (int i = 0; i < attributeCount; i++) {
					String attribLocalName = attributes.getLocalName(i);
					currentNode.setAttribute(attribLocalName, attributes.getValue(i));
				}
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (!nodeStack.isEmpty()) {
				currentNode = nodeStack.pop();
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			currentNode.setValue(new String(ch, start, length));
		}

	}

	/**
	 * The {@link XMLReader} backing this {@link XmlParser}.
	 */
	private final XMLReader xmlReader;

	/**
	 * The SAX event handler.
	 */
	private final XmlHandler eventHandler;

	/**
	 * The current root node.
	 */
	private Node rootNode;

	/**
	 * The current node.
	 */
	private Node currentNode;
	
	/**
	 * The input stream.
	 */
	private InputStream inputStream;

	/**
	 * The stack of nodes, which is used when traversing the document and going
	 * through child nodes.
	 */
	private Stack<Node> nodeStack = new Stack<Node>();

	/**
	 * Creates a new xml parser.
	 * @throws SAXException if a SAX error occurs.
	 */
	public XmlParser(InputStream inputStream) throws SAXException {
		this.inputStream = inputStream;
		xmlReader = XMLReaderFactory.createXMLReader();
		eventHandler = this.new XmlHandler();
		init();
	}
	
	@Override
	public Node parse() throws IOException {
		synchronized (this) {
			try {
				return parse(new InputSource(inputStream));
			} catch (SAXException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * Initialises this parser.
	 */
	private void init() {
		xmlReader.setContentHandler(eventHandler);
		xmlReader.setDTDHandler(eventHandler);
		xmlReader.setEntityResolver(eventHandler);
		xmlReader.setErrorHandler(eventHandler);
	}

	/**
	 * Parses XML data from the {@link InputSource}.
	 * @param source The {@link InputSource}.
	 * @return The root {@link Node}.
	 * @throws IOException if an I/O error occurs.
	 * @throws SAXException if a SAX error occurs.
	 */
	private Node parse(InputSource source) throws IOException, SAXException {
		rootNode = null;
		xmlReader.parse(source);
		if (rootNode == null) {
			throw new SAXException("no root element!");
		}
		return rootNode;
	}

}

