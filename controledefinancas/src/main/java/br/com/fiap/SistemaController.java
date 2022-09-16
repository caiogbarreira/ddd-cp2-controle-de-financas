package br.com.fiap;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.com.fiap.model.Conta;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SistemaController implements Initializable{
    
    // Cadastro de Contas
    private int nextID = 0;
    @FXML TextField txfConta;
    @FXML TextField txfValor;
    @FXML DatePicker dtVencimento;
    @FXML ChoiceBox<String> cbxCategoria;
    @FXML CheckBox cbxStatus;
    private String[] categorias = {"Alimentação", "Educação", "Lazer", "Moradia", "Saúde", "Transporte", "Outros"};
    private List<Conta> contas = new ArrayList<Conta>();
    private List<Conta> contasPagas = new ArrayList<Conta>();
    private List<Conta> contasNaoPagas = new ArrayList<Conta>();
    


    public void cadastrarContas() {
        try {
            validarCampos();
            double valor = Double.parseDouble(txfValor.getText());
            String nomeConta = txfConta.getText();  
            String validade = dtVencimento.getValue().toString();
            String categoria = cbxCategoria.getValue();
            boolean paga = cbxStatus.isSelected();
            Conta conta = new Conta(nextID, nomeConta, valor, validade, categoria, paga);
            nextID++;
            contas.add(conta);
            if (paga) {
                contasPagas.add(conta);
            } else {
                contasNaoPagas.add(conta);
            }
            atualizarTabela();
            // limparCampos();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cadastro de Contas");
            alert.setHeaderText("Conta cadastrada com sucesso!");
            alert.showAndWait();
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao cadastrar conta");
            alert.setContentText("O valor da conta inserida é inválido");
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao cadastrar conta");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void validarCampos() throws IllegalArgumentException {
        if (txfConta.getText().isEmpty()) {
            throw new IllegalArgumentException("O campo conta é obrigatório");
        }
        if (txfValor.getText().isEmpty()) {
            throw new IllegalArgumentException("O campo valor é obrigatório");
        }
        if (dtVencimento.getValue() == null) {
            throw new IllegalArgumentException("O campo vencimento é obrigatório");
        }
        if (cbxCategoria.getValue() == null) {
            throw new IllegalArgumentException("O campo categoria é obrigatório");
        }
    }

    
    public void limparCampos() {
        txfConta.setText("");
        txfValor.setText("");
        dtVencimento.setValue(null);
        cbxStatus.setSelected(false);
        setCategorias();
    }

    public void setCategorias(){
        cbxCategoria.getItems().clear();
        cbxCategoria.getItems().addAll(categorias);
    }
    
    // Visualizar Contas
    
    @FXML TableView<Conta> tblContas;
    @FXML TableView<Conta> tblContasPagas;
    @FXML TableView<Conta> tblContasNaoPagas;

    @FXML TableColumn<Conta, Integer> tblcID;
    @FXML TableColumn<Conta, String> tblcNome;
    @FXML TableColumn<Conta, Double> tblcValor;
    @FXML TableColumn<Conta, String> tblcVencimento;
    @FXML TableColumn<Conta, String> tblcCategoria;
    @FXML TableColumn<Conta, String> tblcPago;
    
    @FXML TableColumn<Conta, Integer> tblcID1;
    @FXML TableColumn<Conta, String> tblcNome1;
    @FXML TableColumn<Conta, Double> tblcValor1;
    @FXML TableColumn<Conta, String> tblcVencimento1;
    @FXML TableColumn<Conta, String> tblcCategoria1;
    @FXML TableColumn<Conta, String> tblcPago1;

    @FXML TableColumn<Conta, Integer> tblcID11;
    @FXML TableColumn<Conta, String> tblcNome11;
    @FXML TableColumn<Conta, Double> tblcValor11;
    @FXML TableColumn<Conta, String> tblcVencimento11;
    @FXML TableColumn<Conta, String> tblcCategoria11;
    @FXML TableColumn<Conta, String> tblcPago11;
    @FXML Button removerConta;
    @FXML Button pagarConta;
    
    public void atualizarTabela() {
        tblContas.getColumns().clear();
        tblContas.getItems().clear();
        tblContas.getColumns().addAll(tblcID, tblcNome, tblcValor, tblcVencimento, tblcCategoria, tblcPago);
        tblContas.getItems().addAll(contas);

        tblcID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        tblcNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeConta()));
        tblcValor.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getValor()).asObject());
        tblcVencimento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValidade()));
        tblcCategoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria()));
        tblcPago.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isPaga() ? "Sim" : "Não"));

        tblContasPagas.getColumns().clear();
        tblContasPagas.getItems().clear();
        tblContasPagas.getColumns().addAll(tblcID1, tblcNome1, tblcValor1, tblcVencimento1, tblcCategoria1, tblcPago1);
        tblContasPagas.getItems().addAll(contasPagas);

        tblcID1.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        tblcNome1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeConta()));
        tblcValor1.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getValor()).asObject());
        tblcVencimento1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValidade()));
        tblcCategoria1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria()));
        tblcPago1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isPaga() ? "Sim" : "Não"));

        tblContasNaoPagas.getColumns().clear();
        tblContasNaoPagas.getItems().clear();
        tblContasNaoPagas.getColumns().addAll(tblcID11, tblcNome11, tblcValor11, tblcVencimento11, tblcCategoria11, tblcPago11);
        tblContasNaoPagas.getItems().addAll(contasNaoPagas);

        tblcID11.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        tblcNome11.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeConta()));
        tblcValor11.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getValor()).asObject());
        tblcVencimento11.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValidade()));
        tblcCategoria11.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria()));
        tblcPago11.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isPaga() ? "Sim" : "Não"));
        
    }
    
    public void removerConta() {
        Conta conta = tblContas.getSelectionModel().getSelectedItem();
        Conta contaPaga = tblContasPagas.getSelectionModel().getSelectedItem();
        Conta contaNaoPaga = tblContasNaoPagas.getSelectionModel().getSelectedItem();

        if (conta != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Remover conta");
            alert.setContentText("Deseja realmente remover a conta selecionada?");
            var resultado = alert.showAndWait();
            if (resultado.get().getText().equals("OK")) {
                contas.remove(conta);
                if (conta.isPaga()) {
                    contasPagas.remove(conta);
                } else {
                    contasNaoPagas.remove(conta);
                }
                atualizarTabela();
            }
        } else if (contaPaga != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Remover conta");
            alert.setContentText("Deseja realmente remover a conta selecionada?");
            var resultado = alert.showAndWait();
            if (resultado.get().getText().equals("OK")) {
                contas.remove(contaPaga);
                if (contaPaga.isPaga()) {
                    contasPagas.remove(contaPaga);
                } else {
                    contasNaoPagas.remove(contaPaga);
                }
                atualizarTabela();
            }
        } else if (contaNaoPaga != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Remover conta");
            alert.setContentText("Deseja realmente remover a conta selecionada?");
            var resultado = alert.showAndWait();
            if (resultado.get().getText().equals("OK")) {
                contas.remove(contaNaoPaga);
                if (contaNaoPaga.isPaga()) {
                    contasPagas.remove(contaNaoPaga);
                } else {
                    contasNaoPagas.remove(contaNaoPaga);
                }
                atualizarTabela();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Nenhuma conta selecionada");
            alert.setContentText("Selecione uma conta para remover");
            alert.showAndWait();
        }
    }

    public void pagarConta(){
        Conta conta = tblContas.getSelectionModel().getSelectedItem();

        if(conta != null){
            if(conta.isPaga()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao pagar conta");
                alert.setContentText("A conta selecionada já está paga");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmação");
                alert.setHeaderText("Pagar conta");
                alert.setContentText("Deseja realmente pagar a conta selecionada?");
                var resultado = alert.showAndWait();
                if (resultado.get().getText().equals("OK")) {
                    conta.setPaga(true);
                    atualizarTabela();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao pagar conta");
            alert.setContentText("Nenhuma conta selecionada");
            alert.showAndWait();
        }
    }

    public void sobreAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sobre");
        alert.setHeaderText("Sobre o programa");
        alert.setContentText("Desenvolvido por: Caio Gallo Barreira | RM94526\nFIAP - 2022");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        removerConta.setDisable(true);
        removerConta.disableProperty().bind(Bindings.isEmpty(tblContas.getSelectionModel().getSelectedItems()).and(Bindings.isEmpty(tblContasPagas.getSelectionModel().getSelectedItems()).and(Bindings.isEmpty(tblContasNaoPagas.getSelectionModel().getSelectedItems()))));
        pagarConta.setDisable(true);
        pagarConta.disableProperty().bind(Bindings.isEmpty(tblContas.getSelectionModel().getSelectedItems()).and(Bindings.isEmpty(tblContasPagas.getSelectionModel().getSelectedItems()).and(Bindings.isEmpty(tblContasNaoPagas.getSelectionModel().getSelectedItems()))));

        setCategorias();  
    }

}
