package br.com.fiap;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.com.fiap.model.Conta;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class PrimaryController implements Initializable{
    
    // Cadastro de Contas
    private int nextID = 0;
    @FXML TextField txfConta;
    @FXML TextField txfValor;
    @FXML DatePicker dtVencimento;
    @FXML ChoiceBox<String> cbxCategoria;
    @FXML CheckBox cbxStatus;
    private String[] categorias = {"Alimentação", "Educação", "Lazer", "Moradia", "Saúde", "Transporte", "Outros"};
    private List<Conta> contas = new ArrayList<Conta>();

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
            atualizarTabela(contas);
            limparCampos();
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

    public void atualizarTabela(List<Conta> listaContas) {
        tblContas.getItems().clear();
        tblContas.getItems().addAll(listaContas);
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

    @FXML ListView<Conta> tblContas;
    @FXML Button removerConta;
    @FXML Button pagarConta;
    @FXML Button ordenarValor;
    @FXML Button ordenarVencimento;
    @FXML Button ordenarCategoria;
    @FXML Button ordenarStatus;

    public void removerConta() {
        Conta conta = tblContas.getSelectionModel().getSelectedItem();
        if (conta != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Remover conta");
            alert.setContentText("Deseja realmente remover a conta selecionada?");
            var resultado = alert.showAndWait();
            if (resultado.get().getText().equals("OK")) {
                contas.remove(conta);
                atualizarTabela(contas);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao remover conta");
            alert.setContentText("Nenhuma conta selecionada");
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
                    atualizarTabela(contas);
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

    private int cNum = 0;

    public void ordenarPorValor() {
        if (cNum == 1) {
            contas.sort((c1, c2) -> Double.compare(c2.getValor(), c1.getValor()));
            cNum = 0;
        } else {
            contas.sort((c1, c2) -> Double.compare(c1.getValor(), c2.getValor()));
            cNum = 1;
        }
        atualizarTabela(contas);
    }

    public void ordenarPorVencimento() {
        if (cNum == 2) {
            contas.sort((c1, c2) -> c2.getValidade().compareTo(c1.getValidade()));
            cNum = 0;
        } else {
            contas.sort((c1, c2) -> c1.getValidade().compareTo(c2.getValidade()));
            cNum = 2;
        }
        atualizarTabela(contas);
    }

    public void ordenarPorCategoria() {
        if (cNum == 3) {
            contas.sort((c1, c2) -> c2.getCategoria().compareTo(c1.getCategoria()));
            cNum = 0;
        } else {
            contas.sort((c1, c2) -> c1.getCategoria().compareTo(c2.getCategoria()));
            cNum = 3;
        }
        atualizarTabela(contas);
    }

    public void ordenarPorStatus(){
        if (cNum == 4) {
            contas.sort((c1, c2) -> Boolean.compare(c2.isPaga(), c1.isPaga()));
            cNum = 0;
        } else {
            contas.sort((c1, c2) -> Boolean.compare(c1.isPaga(), c2.isPaga()));
            cNum = 4;
        }
        atualizarTabela(contas);
    }

    public void ordenarPorID(){
        cNum = 0;
        contas.sort((c1, c2) -> Integer.compare(c1.getId(), c2.getId()));
        atualizarTabela(contas);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        removerConta.setDisable(true);
        removerConta.disableProperty().bind(Bindings.isEmpty(tblContas.getSelectionModel().getSelectedItems()));
        pagarConta.setDisable(true);
        pagarConta.disableProperty().bind(Bindings.isEmpty(tblContas.getSelectionModel().getSelectedItems()));

        setCategorias();  
    }

}
