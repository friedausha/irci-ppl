package IRCI;

import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.samples.PluggableRendererDemo;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.Element;
import org.jdom2.Document;

import javax.swing.*;
import java.io.*;
import java.util.*;


import java.io.File;


public class MenuController{

    private Button InsertMetadataButton = new Button();
    private Button ViewGraphButton = new Button();
    final FileChooser fileChooser = new FileChooser();
    public TextField SearchTextField = new TextField();
    public Button SearchbyNameButton = new Button();
    public Button SearchbyTopicButton = new Button();
    public static Map<String, Article> articleMap = new HashMap<String, Article>();

    @FXML
    public void initialize() {
        InsertMetadataButton.setOnAction(e -> displayForm());
        ViewGraphButton.setOnAction(e->readRan());
        SearchbyNameButton.setOnAction(e->filterByName(SearchTextField.getText()));
        SearchbyTopicButton.setOnAction(e->filterByTopic(SearchTextField.getText()));
    }

    @FXML
    public void displayForm() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            List<File> file = new ArrayList<File>();
            file = fileChooser.showOpenMultipleDialog(stage);
            if (file != null) {
                openFile(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openFile(List<File> file) {
        for (int i = 0; i < file.size(); i++) {
            convertMetadata(file.get(i));
        }
    }

    @FXML
    public void convertMetadata(File file) {
        boolean thrown = false;
        List<Article> metadata = new ArrayList<>();
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = (Document) builder.build(file);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("article");

            for (int i = 0; i < list.size(); i++) {

                Element node = (Element) list.get(i);
                Article article = new Article();
                article.setTitle(node.getChildText("title"));
//                article.setAbstr(node.getChildText("abstract"));
                article.setAuthor(node.getChildText("author"));
                article.setKeyword(node.getChildText("keyword"));
                article.setReferences(node.getChildText("references"));
                article.setYear(node.getChildText("year"));
                metadata.add(article);
            }

        } catch (JDOMException jdomex) {
            thrown = true;
            System.out.println(jdomex.getMessage());
        } catch (IOException e) {
            thrown = true;
            e.printStackTrace();
        }
        if(!thrown){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Submission status");
            alert.setHeaderText("Information Alert");
            String s ="Submission is completed ";
            alert.setContentText(s);
            alert.showAndWait();
            saveMetadata(metadata);
        }
        else {
            showDialogBox();
        }
    }
    @FXML
    public void showDialogBox(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("DialogBox.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void saveMetadata(List<Article> metadata) {
        try{
            FileWriter writer = new FileWriter("metadatas.ran", true);
            BufferedWriter out = new BufferedWriter(writer);
            for (int i = 0; i < metadata.size(); i++) {
                System.out.println(metadata.get(i).getTitle());
                out.write(metadata.get(i).getTitle());
                out.write(metadata.get(i).getAuthor());
                out.write(metadata.get(i).getKeyword());
//                out.write(metadata.get(i).getAbstr());
                out.write(metadata.get(i).getYear());
                out.write(metadata.get(i).getReferences());
            }
            out.close();
            ReclusterMetadata();
        } catch(IOException ex){
            ex.printStackTrace();
        } catch (RuntimeException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    public void ReclusterMetadata(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Recluster");
        alert.setHeaderText("Recluster Metadata?");

        ButtonType Recluster = new ButtonType("Recluster", ButtonBar.ButtonData.APPLY);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(Recluster, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == Recluster){
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Recluster");
            alert1.setHeaderText("Replace Existing Classes?");

            ButtonType Replace = new ButtonType("Replace", ButtonBar.ButtonData.APPLY);
            ButtonType buttonTypeCancel1 = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert1.getButtonTypes().setAll(Replace, buttonTypeCancel1);
            Optional<ButtonType> result1 = alert1.showAndWait();
            if(result1.get() == Replace){
                readRan();
            }
        }  else {
            System.out.println("cancel");
        }
    }
    @FXML
    public void readRan(){
        String fileName = "metadatas.ran";
        String line;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Article temp = new Article();
            int i=1;
            while((line = bufferedReader.readLine()) != null) {
                if(i==2) temp.setTitle(line);
                else if(i==4){
                    temp.setAuthor(line);
                }
                else if(i==6) temp.setKeyword(line);
                else if(i==8) temp.setYear(line);
                else if(i==10) {
                    temp.setReferences(line);
                    temp.setReferredtimes(0);
                    articleMap.put((temp.getTitle()),temp);
                    temp = new Article();
                    i-=10;
                }
                i+=1;
            }
            createGraph(articleMap);
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Classes");
            alert.setHeaderText("No Classes Alert");
            String s ="No classes available. Please recluster the article before viewing graph";
            alert.setContentText(s);
            alert.show();
        }
        catch(IOException ex) {
             ex.printStackTrace();
        }
    }

    @FXML
    public void filterByName(String keyword){
        String fileName = "metadatas.ran";
        String line;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Article temp = new Article();
            int i=1;
            while((line = bufferedReader.readLine()) != null) {
                if(i==2) temp.setTitle(line);
                else if(i==4){
                    temp.setAuthor(line);
                }
                else if(i==6) temp.setKeyword(line);
                else if(i==8) temp.setYear(line);
                else if(i==10) {
                    temp.setReferences(line);
                    boolean isFound = (temp.getAuthor()).indexOf(keyword) != -1 ? true : false;
                    if(isFound){
                        articleMap.put((temp.getTitle()),temp);
                    }
                    temp = new Article();
                    i-=10;
                }
                i+=1;
            }
            createGraph(articleMap);
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

    }
    @FXML
    public void filterByTopic(String keyword){
        String fileName = "metadatas.ran";
        String line;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Article temp = new Article();
            int i=1;
            while((line = bufferedReader.readLine()) != null) {
                if(i==2) temp.setTitle(line);
                else if(i==4){
                    temp.setAuthor(line);
                }
                else if(i==6) temp.setKeyword(line);
                else if(i==8) temp.setYear(line);
                else if(i==10) {
                    temp.setReferences(line);
                    boolean isFound = (temp.getKeyword()).indexOf(keyword) != -1 ? true : false;
                    if(isFound){
                        articleMap.put((temp.getTitle()),temp);
                    }
                    temp = new Article();
                    i-=10;
                }
                i+=1;
            }
            createGraph(articleMap);
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void createGraph(Map<String,Article> articleMap) {
        Graph<String, String > graph = new DirectedSparseMultigraph<String, String>();
        Article temp = new Article();
        String reference;
        String authors;
        int j = 1;
        for(Map.Entry m:articleMap.entrySet()){
            graph.addVertex((String)m.getKey());
            temp = (Article)m.getValue();
            reference = temp.getReferences();
            if(articleMap.containsKey(reference)){
                int x = articleMap.get(reference).getReferredtimes();
//                System.out.println(x);
                articleMap.get(reference).setReferredtimes(x+1);
            }
            authors = temp.getAuthor();
            String number = Integer.toString(j);
            graph.addEdge("circle"+ number, (String) temp.getTitle(), reference);
            graph.addEdge("rectangle" + number, (String) temp.getTitle(), authors);
            j++;
        }
        viewGraph(graph);
    }

    @FXML
    private void viewGraph(Graph<String, String> graph){
        ViewGraph demo = new ViewGraph();
        JFrame frame = new JFrame("IRCI Graph");
        frame.getContentPane().add(demo);
        demo.init(graph);
        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);;
    }
}

