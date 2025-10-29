package backupProceso;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;

public class HiloBackupHistorico implements Runnable {
	
	private final String historialF = "HISTORIAL";
	private final String fechaF = "FECHA";
	private final String nivelF = "NIVEL";
	private final String ratioF = "RATIOCOMPLETACION";
	private final String tiempoF = "TIEMPO";
	private final String tiempoEsperadoF = "TIEMPOESPERADO";
	private final String userIDF = "USERID";
	private final String workoutIDF = "WORKOUTID";
	private final String workoutNombreF = "WORKOUTNOMBRE";
	private final String nombre = "NOMBRE";
	private final String idF = "ID";
	

    private List<Map<String, Object>> historial;

    public HiloBackupHistorico(List<Map<String, Object>> historial) {
        this.historial = historial;
    }

    private void guardarHistorialComoXML(List<Map<String, Object>> historial) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element rootElement = doc.createElement(historialF);
            doc.appendChild(rootElement);

            for (Map<String, Object> h : historial) {
                Element registro = doc.createElement("REGISTRO");

                // ID del documento
                Element idDoc = doc.createElement(idF);
                idDoc.appendChild(doc.createTextNode(String.valueOf(h.get(idF))));
                registro.appendChild(idDoc);

                // Fecha
                Element fecha = doc.createElement(fechaF);
                fecha.appendChild(doc.createTextNode(String.valueOf(h.get(fechaF))));
                registro.appendChild(fecha);

                // Nivel
                Element nivel = doc.createElement(nivelF);
                nivel.appendChild(doc.createTextNode(String.valueOf(h.get(nivelF))));
                registro.appendChild(nivel);

                // Ratio de completación
                Element ratio = doc.createElement(ratioF);
                ratio.appendChild(doc.createTextNode(String.valueOf(h.get(ratioF))));
                registro.appendChild(ratio);

                // Tiempo
                Element tiempo = doc.createElement(tiempoF);
                tiempo.appendChild(doc.createTextNode(String.valueOf(h.get(tiempoF))));
                registro.appendChild(tiempo);

                // Tiempo esperado
                Element tiempoEsperado = doc.createElement(tiempoEsperadoF);
                tiempoEsperado.appendChild(doc.createTextNode(String.valueOf(h.get(tiempoEsperadoF))));
                registro.appendChild(tiempoEsperado);

                // ID del usuario
                DocumentReference usuarioRef = (DocumentReference) h.get(userIDF);
                Element idUsuario = doc.createElement(userIDF);
                idUsuario.appendChild(doc.createTextNode(usuarioRef != null ? usuarioRef.getId() : "desconocido"));
                registro.appendChild(idUsuario);

                // ID del workout
                DocumentReference workoutRef = (DocumentReference) h.get(workoutIDF);
                Element idWorkout = doc.createElement(workoutIDF);
                idWorkout.appendChild(doc.createTextNode(workoutRef != null ? workoutRef.getId() : "desconocido"));
                registro.appendChild(idWorkout);

                // Nombre del workout desde referencia
                String workoutNombreStr = "Desconocido";
                DocumentReference workoutNombreRef = (DocumentReference) h.get(workoutNombreF);
                if (workoutNombreRef != null) {
                    try {
                        DocumentSnapshot workoutDoc = workoutNombreRef.get().get();
                        if (workoutDoc.exists() && workoutDoc.contains(nombre)) {
                            workoutNombreStr = workoutDoc.getString(nombre);
                        }
                    } catch (Exception e) {
                        System.out.println("Error al obtener nombre del workout: " + e.getMessage());
                    }
                }
                Element workoutNombre = doc.createElement(workoutNombreF);
                workoutNombre.appendChild(doc.createTextNode(workoutNombreStr));
                registro.appendChild(workoutNombre);

                rootElement.appendChild(registro);
            }

            // Guardar XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("lib/historial.xml"));
            transformer.transform(source, result);

            System.out.println("Historial guardado correctamente en historial.xml");

        } catch (Exception e) {
            System.out.println("Error al guardar historial como XML: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        guardarHistorialComoXML(historial);
    }
}
