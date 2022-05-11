//package repository.XMLRepositories;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.NodeList;
//
//public class PurchaseXMLRepository extends AbstractXMLRepository<Long, Student> {
//    /**
//     * Class constructor
//     * @param filename - numele fisierului
//     */
//    public StudentXMLRepo(Validator<Student> validator,String filename) {
//        super(validator,filename);
//    }
//
//    /**
//     * Extrage informatia despre student dintr-un element XML
//     * @param element - XML-ul din care ia datele studentului
//     * @return studentul
//     */
//
//    @Override
//    public Student extractEntity(Element element) {
//        String studentId = element.getAttribute("idStudent");
//        NodeList nods = element.getChildNodes();
//        String nume =element.getElementsByTagName("nume")
//                .item(0)
//                .getTextContent();
//        String grupa =element.getElementsByTagName("grupa")
//                .item(0)
//                .getTextContent();
//        String email =element.getElementsByTagName("email")
//                .item(0)
//                .getTextContent();
//        String numeProfesor = element.getElementsByTagName("numeProfesor")
//                .item(0)
//                .getTextContent();
//
//        return new Student(Long.parseLong(studentId), nume, Integer.parseInt(grupa), email, numeProfesor);
//    }
//
//    @Override
//    public Element createElementfromEntity(Document document, Student entity) {
//        Element e = document.createElement("student");
//        e.setAttribute("idStudent", entity.getId().toString());
//
//        Element nume = document.createElement("nume");
//        nume.setTextContent(entity.getNume());
//        e.appendChild(nume);
//
//        Element grupa = document.createElement("grupa");
//        Integer g=entity.getGrupa();
//        grupa.setTextContent(g.toString());
//        e.appendChild(grupa);
//
//        Element email = document.createElement("email");
//        email.setTextContent(entity.getEmail());
//        e.appendChild(email);
//
//        Element numeProfesor = document.createElement("numeProfesor");
//        numeProfesor.setTextContent(entity.getNumeProfesor());
//        e.appendChild(numeProfesor);
//
//        return e;
//    }
//
//
//}
