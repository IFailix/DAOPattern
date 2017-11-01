package dataLayer.dataAccessObjects.xml;

import businessObjects.ITrainer;
import dataLayer.businessObjects.Trainer;
import dataLayer.dataAccessObjects.ITrainerDao;
import exceptions.NoNextTrainerFoundException;
import exceptions.NoPreviousTrainerFoundException;
import exceptions.NoTrainerFoundException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrainerDaoXml implements ITrainerDao {

    private List<ITrainer> trainerList;

    public TrainerDaoXml() throws ParserConfigurationException, SAXException, IOException {
        this.trainerList = new ArrayList<ITrainer>();
        this.trainerList = getTrainerFromXml();
    }

    @Override
    public ITrainer create() {
        ITrainer trainer = new Trainer();
        trainerList.add(trainer);
        return trainer;
    }

    @Override
    public void delete(ITrainer trainer) {
        trainerList.remove(trainer);
    }

    @Override
    public ITrainer first() throws NoTrainerFoundException {
        if (trainerList.isEmpty()) {
            throw new NoTrainerFoundException("Kein Trainer gefunden!");
        }
        return trainerList.get(0);
    }

    @Override
    public ITrainer last() throws NoTrainerFoundException {
        if (trainerList.isEmpty()) {
            throw new NoTrainerFoundException("Kein Trainer gefunden!");
        }
        return trainerList.get(trainerList.size() - 1);
    }

    @Override
    public ITrainer next(ITrainer trainer) throws NoNextTrainerFoundException {
        if (trainerList.indexOf(trainer) < trainerList.size() - 1) {
            throw new NoNextTrainerFoundException("Es gibt keine weiteren Trainer!");
        }
        return trainerList.get(trainerList.indexOf(trainer) + 1);
    }

    @Override
    public ITrainer previous(ITrainer trainer) throws NoPreviousTrainerFoundException {
        if (trainerList.indexOf(trainer) > 0) {
            throw new NoPreviousTrainerFoundException("Es gibt keine weiteren Trainer!");
        }
        return trainerList.get(trainerList.indexOf(trainer) - 1);
    }

    @Override
    public void save(ITrainer trainer) throws IOException {

        for (ITrainer person : trainerList) {
            if (person.getId() == trainer.getId()) {
                trainerList.remove(person);
                break;
            }
        }
        trainerList.add(trainer);

        this.saveAsXml();
    }

    public void saveAsXml() {
        try {

            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();
            Document doc = build.newDocument();

            Element root = doc.createElement("Trainers");

            for (ITrainer trainer : this.trainerList) {

                Element trainerElement = doc.createElement("Trainer");
                trainerElement.setAttribute("id", String.valueOf(trainer.getId()));
                trainerElement.setAttribute("name", trainer.getName());
                trainerElement.setAttribute("alter", String.valueOf(trainer.getAlter()));
                trainerElement.setAttribute("erfahrung", String.valueOf(trainer.getErfahrung()));
                root.appendChild(trainerElement);
            }

            doc.appendChild(root);
            writeEmptyXmlFile(doc);


        } catch (TransformerException ex) {
            System.out.println("Error outputting document");

        } catch (ParserConfigurationException ex) {
            System.out.println("Error building document");
        }
    }

    private void writeEmptyXmlFile(Document doc) throws TransformerException {
        // Save the document to the disk file
        TransformerFactory tranFactory = TransformerFactory.newInstance();
        Transformer aTransformer = tranFactory.newTransformer();

        // format the XML nicely
        aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

        aTransformer.setOutputProperty(
                "{http://xml.apache.org/xslt}indent-amount", "4");
        aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        try {
            // location and name of XML file you can change as per need
            FileWriter trainerFile = new FileWriter("trainer.xml");
            StreamResult result = new StreamResult(trainerFile);
            aTransformer.transform(source, result);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public List<ITrainer> select() {
        return trainerList;
    }

    @Override
    public ITrainer select(int id) throws NoTrainerFoundException {
        for (ITrainer trainer : trainerList) {
            if (trainer.getId() == id) {
                return trainer;
            }
        }
        throw new NoTrainerFoundException("Kein Trainer gefunden!");
    }

    public List<ITrainer> getTrainerFromXml() throws IOException, ParserConfigurationException, SAXException {
        List<ITrainer> tempList = new ArrayList<ITrainer>();
        File trainerFile = new File("trainer.xml");
        if (!trainerFile.exists()) {
            return tempList;
        }
        //TODO add export empty xml
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(trainerFile);
        Node trainersNode = doc.getElementsByTagName("Trainers").item(0).getChildNodes().item(0);

        Node node = trainersNode;
        while (node != null)
        {
            Element element = (Element) node;
            Trainer trainer = new Trainer();
            trainer.setId(Integer.parseInt(element.getAttribute("id")));
            trainer.setName(element.getAttribute("name"));
            trainer.setAlter(Integer.parseInt(element.getAttribute("alter")));
            trainer.setErfahrung(Integer.parseInt(element.getAttribute("erfahrung")));
            tempList.add(trainer);
            node = node.getNextSibling();
        }
        return tempList;
    }
}

