package sample

import org.w3c.dom.*
import kotlin.browser.document
import kotlin.browser.window

@JsName("formatar")
fun formatar(mascara: String, elemento: HTMLInputElement) {
    var campodetexto = elemento //document.getElementById("cpf_input") as HTMLInputElement

    campodetexto.maxLength = 14

    campodetexto.onkeypress = {
        var i = campodetexto.value.length;
        var saida = mascara.substring(0, 1);
        var texto = mascara.substring(i)

        if (texto.substring(0, 1) != saida) {
            campodetexto.value += texto.substring(0, 1);
        }
    }

}

fun auxValidateCadastro(cpfInput: String, senha1: String, senha2: String): Boolean {
    if (senha1 != senha2) {
        window.alert("Senhas diferentes")
        return false
    } else if (cpfInput.length < 14) {
        window.alert("CPF muito pequeno")
        return false
    } else if (senha1.length < 6) {
        window.alert("senha deve ter mais que 6 digitos")
        return false
    } else cpfInput.forEach { x ->
        if (x != '1' && x != '2' && x != '3' && x != '4' && x != '5' && x != '6' && x != '7'
            && x != '8' && x != '9' && x != '0' && x != '.' && x != '-'
        ) {
            window.alert("Formato de CPF invalido")
            return false
        }
    }
    return true

}

fun validateCadastro() {
    var form = document.getElementById("formulario_cadastro") as HTMLFormElement
    var cpfInput = form.get("CPF") as HTMLInputElement
    var senha1 = form.get("senha") as HTMLInputElement
    var senha2 = form.get("senha2") as HTMLInputElement
    var submitInput = document.getElementById("cadastrarSubmit") as HTMLInputElement

    submitInput.onclick = {
        if (auxValidateCadastro(cpfInput.value, senha1.value, senha2.value))
            form.submit()
    }
}


fun main() {
    println(document.title)
    if (document.title == "Tela de Cadastro") {
        formatar("###.###.###-##", document.getElementById("cpf_input_cadastro") as HTMLInputElement)
        if(document.getElementById("erro")!=null){
            window.alert("cpf já usado")
            document.location?.href ="/cadastrar"
        }

        validateCadastro()
    } else if (document.title == "POLI Class - LPF") {
        formatar("###.###.###-##", document.getElementById("cpf_input") as HTMLInputElement)
        if ((document.getElementById("mostrar aviso conta criada"))!=null) {
            window.alert("conta criada com sucesso")
            document.location?.href = "/"
        }else if(document.getElementById("login nao encontrado")!=null){
            window.alert("conta não encontrada")
            document.location?.href = "/"
        }
    }
}