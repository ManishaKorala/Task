import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box.Filler;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLParser {

	public Element XMLParsing(){

		System.out.println("MAIN Started");

		FeatureModel featureModel = new FeatureModel();

		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("feature_xml.txt");
		Element condition = null;
		try {

			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement(); // feature Model
			rootNode.getChildren();

			for (int i = 0; i < rootNode.getChildren().size(); i++) {
				if (rootNode.getChildren().get(i).getName().equals("struct")) {
					Struct newStuct = new Struct();

					condition = rootNode.getChildren().get(0).getChildren().get(0);
					System.out.println("main node: " + condition.getName());

					System.out.println();
					Condition condObj = new Condition();
					
					if (condition.getAttributeValue("mandatory") != null) {
						condObj.setMandatory(condition.getAttributeValue("mandatory"));
					}
					if (condition.getAttributeValue("abstract") != null) {
						condObj.setAbstract(condition.getAttributeValue("abstract"));
					}
					fillData(condition, condObj);

					newStuct.getConditions().add(condObj);
					featureModel.getStruct().add(newStuct);
				} else if (rootNode.getChildren().get(i).getName().equals("struct")) {

				}

			}
			// rootNode.

			// fullList()
		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
		
		

		System.out.println("MAIN ENDED");
		return condition;
	}

	public void fillData(Element currNode, Condition cond) {
		String name;
		
		System.out.println("currNode: " + currNode.getName());
		name = currNode.getAttributeValue("name");
		System.out.println("currNode Ki value: " + name);
		
		System.out.println();
		for (int i = 0; i < currNode.getChildren().size(); i++) {

			if (currNode.getChildren().get(i).getName().equals("feature")) {
				Feature feature = new Feature();
				
				
				if (currNode.getAttributeValue("mandatory") != null) {
					feature.setMandatory(currNode.getAttributeValue("mandatory"));
				}
				
				cond.getFeatures().add(feature);
				System.out.println("feature: " + currNode.getName());
				System.out.println("featureName: " + currNode.getChildren().get(i).getAttributeValue("name"));
//				System.out.println("Mandatory : " + feature.mandatory);
				
				
				System.out.println();
			} else {

				System.out.println("cond: " + currNode.getName());

				System.out.println();
				cond.getConditions().add(
						new Condition(currNode.getAttributeValue("name"), currNode.getAttributeValue("mandatory")));
				fillData(currNode.getChildren().get(i), cond.getConditions().get(cond.getConditions().size() - 1));

			}
		}
	}
}// end XML Parser