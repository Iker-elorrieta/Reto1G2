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

                // ID del documento
                Element idDoc = doc.createElement("id_documento");
                idDoc.appendChild(doc.createTextNode(String.valueOf(h.get("ID"))));
                registro.appendChild(idDoc);

                // Fecha
                Element fecha = doc.createElement("fecha");
                fecha.appendChild(doc.createTextNode(String.valueOf(h.get("FECHA"))));
                registro.appendChild(fecha);

                // Nivel
                Element nivel = doc.createElement("nivel");
                nivel.appendChild(doc.createTextNode(String.valueOf(h.get("NIVEL"))));
                registro.appendChild(nivel);

                // Ratio de completación
                Element ratio = doc.createElement("ratio_complecion");
                ratio.appendChild(doc.createTextNode(String.valueOf(h.get("RATIO_COMPLETACION"))));
                registro.appendChild(ratio);

                // Tiempo
                Element tiempo = doc.createElement("tiempo");
                tiempo.appendChild(doc.createTextNode(String.valueOf(h.get("TIEMPO"))));
                registro.appendChild(tiempo);

                // Tiempo esperado
                Element tiempoEsperado = doc.createElement("tiempo_esperado");
                tiempoEsperado.appendChild(doc.createTextNode(String.valueOf(h.get("TIEMPO_ESPERADO"))));
                registro.appendChild(tiempoEsperado);

                // ID del usuario
                DocumentReference usuarioRef = (DocumentReference) h.get("USUARIO");
                Element idUsuario = doc.createElement("id_usuario");
                idUsuario.appendChild(doc.createTextNode(usuarioRef != null ? usuarioRef.getId() : "desconocido"));
                registro.appendChild(idUsuario);

                // ID del workout
                DocumentReference workoutRef = (DocumentReference) h.get("WORKOUT");
                Element idWorkout = doc.createElement("workout_id");
                idWorkout.appendChild(doc.createTextNode(workoutRef != null ? workoutRef.getId() : "desconocido"));
                registro.appendChild(idWorkout);

                // Nombre del workout desde referencia
                String workoutNombreStr = "Desconocido";
                DocumentReference workoutNombreRef = (DocumentReference) h.get("WORKOUTNOMBRE");
                if (workoutNombreRef != null) {
                    try {
                        DocumentSnapshot workoutDoc = workoutNombreRef.get().get();
                        if (workoutDoc.exists() && workoutDoc.contains("NOMBRE")) {
                            workoutNombreStr = workoutDoc.getString("NOMBRE");
                        }
                    } catch (Exception e) {
                        System.out.println("Error al obtener nombre del workout: " + e.getMessage());
                    }
                }
                Element workoutNombre = doc.createElement("workout_nombre");
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
