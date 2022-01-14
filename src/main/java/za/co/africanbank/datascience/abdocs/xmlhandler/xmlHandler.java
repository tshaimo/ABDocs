package za.co.africanbank.datascience.abdocs.xmlhandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;



public class xmlHandler
{
	public Document stringToJDOMDocument(String inputXML) throws JDOMException, IOException{
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new ByteArrayInputStream(inputXML.getBytes("UTF-8")));
		return document;
	}
	public String jdomElementToString(Element root) throws JDOMException{
		Format fmt = Format.getPrettyFormat();
		fmt.setExpandEmptyElements(true);
		XMLOutputter output = new XMLOutputter(fmt);
		return output.outputString(root);
	}

	public String jdomDocumentToString(Document document) throws JDOMException {
		Format fmt = Format.getPrettyFormat();
		fmt.setExpandEmptyElements(true);
		XMLOutputter output = new XMLOutputter(fmt);
		return output.outputString(document);
	}

	public Element addChildElement(String expectOldParent, String expectNewParent, Element root, Element child) throws JDOMException{
	    if (expectNewParent != null){
	      Element processControl = new Element(expectNewParent);
	      processControl.addContent(child);
	      if (expectOldParent != null) {
	        root.getChild(expectOldParent).addContent(0, processControl);
	      } else {
	        root.addContent(0, child);
	      }
	    }else if (expectOldParent != null){
	      root.getChild(expectOldParent).addContent(0, child);
	    } else{
	      root.addContent(0, child);
	    }
	    return root;
	  }

	public Element addChildContent(String expectOldParent, String expectNewParent, Element root, Element child)
			throws JDOMException
	{
		if (expectNewParent != null)
		{
			Element processControl = new Element(expectNewParent);
			processControl.addContent(child.cloneContent());
			if (expectOldParent != null) {
				root.getChild(expectOldParent).addContent(0, processControl);
			} else if ((expectNewParent != null) && (expectOldParent == null)) {
				root.addContent(0, processControl);
			} else {
				root.addContent(0, child.cloneContent());
			}
		}
		else if (expectOldParent != null)
		{
			root.getChild(expectOldParent).addContent(0, child.cloneContent());
		}
		else
		{
			root.addContent(0, child.cloneContent());
		}
		return root;
	}

	public Element addChildToChildElement(String expectOldParent1, String expectOldParent2, String expectNewParent, Element root, Element child)
			throws JDOMException
	{
		if (expectNewParent != null)
		{
			Element processControl = new Element(expectNewParent);
			processControl.addContent(child);
			root.getChild(expectOldParent1).getChild(expectOldParent2).addContent(0, processControl);
		}
		else
		{
			root.getChild(expectOldParent1).getChild(expectOldParent2).addContent(0, child);
		}
		return root;
	}

	public Element addChildSpecialElement(String expectNewParent, Element root, Element child1, Element child2, Element child3) throws JDOMException{
		Element root1 = new Element(expectNewParent);
		root1.addContent(0, child2);
		root1.addContent(0, child1);
		root1.addContent(0, child3);
		root.addContent(0, root1);
		return root;
	}

	public Element modifyTag(Element element, String oldTag, String newTag, int tagLevel)
			throws JDOMException  {
		Element tag;
		if (tagLevel == 0){
			if (!element.getName().equals(oldTag)) {
				throw new JDOMException("modifyTag: Error - '" + oldTag + "' is not root element of\n" + jdomElementToString(element));
			}
			tag = element;
		}
		else
		{
			tag = element.getChild(oldTag);
			if (tag == null) {
				throw new JDOMException("modifyTag: Error - '" + oldTag + "' element not found as child of\n" + jdomElementToString(element));
			}
		}
		tag.setName(newTag);
		return element;
	}




	public Element getElementByPath(Document document, String nodePath) throws JDOMException, IOException{
		XPathExpression<Element> xpath = XPathFactory.instance().compile(nodePath,Filters.element());
		return xpath.evaluateFirst(document);
	}
	
	public String getElementContentByPath(Document document, String nodePath) throws JDOMException, IOException{
		Element element=getElementByPath(document, nodePath);
		return (element!=null)?jdomElementToString(element):null;
	}
	

	public Element readRequiredElement(Element root, String elementPath) throws JDOMException{
		String[] children = elementPath.split("/");
		Element value = null;
		for (String child:children){
			if (value != null){
				value = value.getChild(child);
			} else{
				value = root.getChild(child);
			}
		}
		if (value == null) {
			throw new JDOMException("read: Error - '"+elementPath+"' element not present in XML");
		}
		return value.clone();
	}
	
	public List<String> getDocumentElementPaths(Document document){
		List<String> nodes=new ArrayList<String>();
		XPathExpression<Element> xpath = XPathFactory.instance().compile("//*[not(*)]",Filters.element());
		for(Element element: xpath.evaluate(document)){
			nodes.add(getXPath(element));
		}
		return nodes;
	}
	
	private String getXPath(Element element) {
		Element parent = element.getParentElement();
		if (parent == null) {
			return element.getName();
		}
		return getXPath(parent) + "/" + element.getName();
	}
	
	public String getFirstEvaluatedElementValue(Document document, String elementName) throws JDOMException, IOException{
		List<String> nodes=getDocumentElementPaths(document);
		for(String node: nodes){
			if(node.contains(elementName)){
				return getElementByPath(document, node).getText();
			}
		}
		return null;
	}
	
	public String getFirstEvaluatedAttributeValue(Document document, String elementName) throws JDOMException, IOException{
		List<String> nodes=getDocumentElementPaths(document);
		for(String node: nodes){
			if(node.contains(elementName)){
				return getElementByPath(document, node).getText();
			}
		}
		return null;
	}
}
