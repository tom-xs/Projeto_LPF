package sample

import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.get
import kotlin.browser.document
import kotlin.browser.window

@JsName("formatar")
fun formatar(mascara: String, elemento: HTMLInputElement) {
    val campodetexto = elemento

    campodetexto.maxLength = 14

    campodetexto.onkeypress = {
        val i = campodetexto.value.length
        val saida = mascara.substring(0, 1)
        val texto = mascara.substring(i)

        if (texto.substring(0, 1) != saida) {
            campodetexto.value += texto.substring(0, 1)
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

fun validateCadastro(): Boolean {
    val form = document.getElementById("formulario_cadastro") as HTMLFormElement
    val cpfInput = form.get("CPF") as HTMLInputElement
    val senha1 = form.get("senha") as HTMLInputElement
    val senha2 = form.get("senha2") as HTMLInputElement

    return auxValidateCadastro(cpfInput.value, senha1.value, senha2.value)

}


fun main() {
    println(document.title)
    if (document.title == "Tela de Cadastro") {
        formatar("###.###.###-##", document.getElementById("cpf_input_cadastro") as HTMLInputElement)
        if (document.getElementById("erro") != null) {
            window.alert("cpf já usado")
            document.location?.href = "/cadastrar"
        }
        (document.getElementById("cadastrarbtn") as HTMLInputElement).onclick = { x ->
            if (validateCadastro() == true)
                (document.getElementById("formulario_cadastro") as HTMLFormElement).submit()
        }

        document.onkeypress = { x ->
            if (x.charCode == 13 && validateCadastro() == true)
                (document.getElementById("formulario_cadastro") as HTMLFormElement).submit()
        }
    } else if (document.title == "POLI Class - LPF") {
        formatar("###.###.###-##", document.getElementById("cpf_input") as HTMLInputElement)
        if ((document.getElementById("mostrar aviso conta criada")) != null) {
            window.alert("conta criada com sucesso")
            document.location?.href = "/"
        } else if (document.getElementById("login nao encontrado") != null) {
            window.alert("conta não encontrada")
            document.location?.href = "/"
        }
    } else if (document.title == "Home") {
        println("pagina Home achada")
        if (document.getElementById("sala nao existe") != null) {
            println("sala nao existe achado")
            window.alert("sala não existe")
            document.location?.href = "/home?CPF=${(document.getElementById("cpfInput") as HTMLInputElement).value}"
        }

    }
}