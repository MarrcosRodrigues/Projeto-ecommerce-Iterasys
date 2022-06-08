# language : pt
Funcionalidade: Visualizr produtos
  Como um usuario nao logado
  Eu que visualizar produtos disponiveis
  Para poder escolher quando eu vou comprar

  Cenario: Deve mostar uma lista de oito produtos na pagina inicial
    Dado que estou na pagina inicial
    Quando nao estou logado
    Entao visualizo 8 produtos disponiveis
    E carrinho esta zerado