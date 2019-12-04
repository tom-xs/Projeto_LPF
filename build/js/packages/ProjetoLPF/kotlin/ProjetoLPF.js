(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'ProjetoLPF'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'ProjetoLPF'.");
    }
    root.ProjetoLPF = factory(typeof ProjetoLPF === 'undefined' ? {} : ProjetoLPF, kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var equals = Kotlin.equals;
  var Unit = Kotlin.kotlin.Unit;
  var unboxChar = Kotlin.unboxChar;
  var throwCCE = Kotlin.throwCCE;
  var println = Kotlin.kotlin.io.println_s8jyv4$;
  var iterator = Kotlin.kotlin.text.iterator_gw00vp$;
  var toBoxedChar = Kotlin.toBoxedChar;
  function formatar$lambda(closure$campodetexto, closure$mascara) {
    return function (it) {
      var i = closure$campodetexto.v.value.length;
      var saida = closure$mascara.substring(0, 1);
      var texto = closure$mascara.substring(i);
      if (!equals(texto.substring(0, 1), saida)) {
        closure$campodetexto.v.value = closure$campodetexto.v.value + texto.substring(0, 1);
      }
      return Unit;
    };
  }
  function formatar(mascara, elemento) {
    var campodetexto = {v: elemento};
    campodetexto.v.maxLength = 14;
    campodetexto.v.onkeypress = formatar$lambda(campodetexto, mascara);
  }
  function auxValidateCadastro(cpfInput, senha1, senha2) {
    if (!equals(senha1, senha2)) {
      window.alert('Senhas diferentes');
      return false;
    }
     else if (cpfInput.length < 14) {
      window.alert('CPF muito pequeno');
      return false;
    }
     else if (senha1.length < 6) {
      window.alert('senha deve ter mais que 6 digitos');
      return false;
    }
     else {
      var tmp$;
      tmp$ = iterator(cpfInput);
      while (tmp$.hasNext()) {
        var element = unboxChar(tmp$.next());
        var x = toBoxedChar(element);
        if (unboxChar(x) !== 49 && unboxChar(x) !== 50 && unboxChar(x) !== 51 && unboxChar(x) !== 52 && unboxChar(x) !== 53 && unboxChar(x) !== 54 && unboxChar(x) !== 55 && unboxChar(x) !== 56 && unboxChar(x) !== 57 && unboxChar(x) !== 48 && unboxChar(x) !== 46 && unboxChar(x) !== 45) {
          window.alert('Formato de CPF invalido');
          return false;
        }
      }
    }
    return true;
  }
  function validateCadastro$lambda(closure$cpfInput, closure$senha1, closure$senha2, closure$form) {
    return function (it) {
      if (auxValidateCadastro(closure$cpfInput.v.value, closure$senha1.v.value, closure$senha2.v.value))
        closure$form.v.submit();
      return Unit;
    };
  }
  function validateCadastro() {
    var tmp$, tmp$_0, tmp$_1, tmp$_2, tmp$_3;
    var form = {v: Kotlin.isType(tmp$ = document.getElementById('formulario_cadastro'), HTMLFormElement) ? tmp$ : throwCCE()};
    var cpfInput = {v: Kotlin.isType(tmp$_0 = form.v['CPF'], HTMLInputElement) ? tmp$_0 : throwCCE()};
    var senha1 = {v: Kotlin.isType(tmp$_1 = form.v['senha'], HTMLInputElement) ? tmp$_1 : throwCCE()};
    var senha2 = {v: Kotlin.isType(tmp$_2 = form.v['senha2'], HTMLInputElement) ? tmp$_2 : throwCCE()};
    var submitInput = Kotlin.isType(tmp$_3 = document.getElementById('cadastrarSubmit'), HTMLInputElement) ? tmp$_3 : throwCCE();
    submitInput.onclick = validateCadastro$lambda(cpfInput, senha1, senha2, form);
  }
  function main() {
    var tmp$, tmp$_0, tmp$_1, tmp$_2, tmp$_3;
    println(document.title);
    if (equals(document.title, 'Tela de Cadastro')) {
      formatar('###.###.###-##', Kotlin.isType(tmp$ = document.getElementById('cpf_input_cadastro'), HTMLInputElement) ? tmp$ : throwCCE());
      if (document.getElementById('erro') != null) {
        window.alert('cpf j\xE1 usado');
        (tmp$_0 = document.location) != null ? (tmp$_0.href = '/cadastrar') : null;
      }
      validateCadastro();
    }
     else if (equals(document.title, 'POLI Class - LPF')) {
      formatar('###.###.###-##', Kotlin.isType(tmp$_1 = document.getElementById('cpf_input'), HTMLInputElement) ? tmp$_1 : throwCCE());
      if (document.getElementById('mostrar aviso conta criada') != null) {
        window.alert('conta criada com sucesso');
        (tmp$_2 = document.location) != null ? (tmp$_2.href = '/') : null;
      }
       else if (document.getElementById('login nao encontrado') != null) {
        window.alert('conta n\xE3o encontrada');
        (tmp$_3 = document.location) != null ? (tmp$_3.href = '/') : null;
      }
    }
  }
  var package$sample = _.sample || (_.sample = {});
  package$sample.formatar = formatar;
  package$sample.auxValidateCadastro_6hosri$ = auxValidateCadastro;
  package$sample.validateCadastro = validateCadastro;
  package$sample.main = main;
  main();
  Kotlin.defineModule('ProjetoLPF', _);
  return _;
}));

//# sourceMappingURL=ProjetoLPF.js.map
