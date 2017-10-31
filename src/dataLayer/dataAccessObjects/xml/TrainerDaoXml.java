package dataLayer.dataAccessObjects.xml;

import businessObjects.ITrainer;
import dataLayer.businessObjects.Trainer;
import dataLayer.dataAccessObjects.ITrainerDao;
import exceptions.NoNextTrainerFoundException;
import exceptions.NoPreviousTrainerFoundException;
import exceptions.NoTrainerFoundException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrainerDaoXml implements ITrainerDao {

    private List<ITrainer> trainerList;

    public TrainerDaoXml() {
        this.trainerList = new ArrayList<ITrainer>();
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
        File file = new File("trainerDatabase.xml");
        if (!file.exists()) {
            file.createNewFile();
        }


        try {
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("trainers");
            doc.appendChild(rootElement);

            // supercars element
            Element supercar = doc.createElement("trainer");
            rootElement.appendChild(supercar);

            // setting attribute to element
            Attr attr = doc.createAttribute("trainer");
            attr.setValue("trainer");
            supercar.setAttributeNode(attr);

            // trainerId element
            Element trainerId = doc.createElement("id");
            Attr attrType = doc.createAttribute("type");
            attrType.setValue("id");
            trainerId.setAttributeNode(attrType);
            trainerId.appendChild(doc.createTextNode(String.valueOf(trainer.getId())));
            supercar.appendChild(trainerId);

            Element trainerName = doc.createElement("name");
            Attr attrType1 = doc.createAttribute("type");
            attrType1.setValue("name");
            trainerName.setAttributeNode(attrType1);
            trainerName.appendChild(doc.createTextNode(trainer.getName()));
            supercar.appendChild(trainerName);

            Element trainerAge = doc.createElement("alter");
            Attr attrType2 = doc.createAttribute("type");
            attrType2.setValue("alter");
            trainerAge.setAttributeNode(attrType2);
            trainerAge.appendChild(doc.createTextNode(String.valueOf(trainer.getAlter())));
            supercar.appendChild(trainerAge);

            Element trainerExp = doc.createElement("erfahrung");
            Attr attrType3 = doc.createAttribute("type");
            attrType3.setValue("erfahrung");
            trainerExp.setAttributeNode(attrType3);
            trainerExp.appendChild(doc.createTextNode(trainer.getName()));
            supercar.appendChild(trainerExp);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("trainer.xml"));
            transformer.transform(source, result);

            // Output to console for testing
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
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
}