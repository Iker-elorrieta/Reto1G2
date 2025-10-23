package backupProceso;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class HiloBackupHistorico implements Runnable {

    private List<Map<String, Object>> historial;

    public HiloBackupHistorico(List<Map<String, Object>> historial) {
        this.historial = historial;
    }

    private void guardarHistorialComoXML(List<Map<String, Object>> historial) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element rootElement = doc.createElement("historico");
            doc.appendChild(rootElement);

            for (Map<String, Object> h : historial) {
                Element registro = doc.createElement("registro");

                Element id = doc.createElement("id_usuario");
                id.appendChild(doc.createTextNode(String.valueOf(h.get("USERID"))));
                registro.appendChild(id);

                Element fecha = doc.createElement("fecha");
                fecha.appendChild(doc.createTextNode(String.valueOf(h.get("FECHA"))));
                registro.appendChild(fecha);

                Element nivel = doc.createElement("nivel");
                nivel.appendChild(doc.createTextNode(String.valueOf(h.get("NIVEL"))));
                registro.appendChild(nivel);

                Element complecion = doc.createElement("ratio_complecion");
                complecion.appendChild(doc.createTextNode(String.valueOf(h.get("RATIOCOMPLETACION"))));
                registro.appendChild(complecion);

                Element tiempo = doc.createElement("tiempo");
                tiempo.appendChild(doc.createTextNode(String.valueOf(h.get("TIEMPO"))));
                registro.appendChild(tiempo);

                Element tiempoEsperado = doc.createElement("tiempo_esperado");
                tiempoEsperado.appendChild(doc.createTextNode(String.valueOf(h.get("TIEMPOESPERADO"))));
                registro.appendChild(tiempoEsperado);

                Element workoutId = doc.createElement("workout_id");
                workoutId.appendChild(doc.createTextNode(String.valueOf(h.get("WORKOUTID"))));
                registro.appendChild(workoutId);

                Element workoutNombre = doc.createElement("workout_nombre");
                workoutNombre.appendChild(doc.createTextNode(String.valueOf(h.get("WORKOUTNOMBRE"))));
                registro.appendChild(workoutNombre);

                rootElement.appendChild(registro);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("lib/historial.xml"));
            transformer.transform(source, result);

            
            System.out.println("Historial guardado correctamente en historial.xml");

        } catch (ParserConfigurationException | javax.xml.transform.TransformerException e) {
            System.out.println("Error al guardar historial como XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        guardarHistorialComoXML(historial);
    }
}
