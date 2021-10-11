# Desafio Bridge_

Foi proposta a construção de uma API utilizando Kotlin e SpringBoot. Eu particularmente não havia tido contato com tais tecnologias, e tive algumas dificuldades ao 
longo do processo. Há muito tempo eu não utilizava outro ambiente de código além do Visual Studio Code, foi interessante utilizar o InteliJ haja vista que é um ambiente 
de programação justamente pensado para trabalhar com Kotlin e Java (pelo que entendi durante seu uso). O mais trabalhoso para mim foi na verdade a etapa de deploy para 
o Heroku, etapa esta que apesar de parecer simples me colocou frente a diversos problemas, acredito que todos eles envolvendo o arquivo `gradle.build`. O erro a seguir 
me impediu de prosseguir com a conclusão do deploy e consequentemente de integrar o [frontend](https://desafio-bridge.vercel.app/) com a endpoint correta, inviabilizando o uso do frontend.

```bash 
2021-10-11T15:08:00.876331+00:00 heroku[web.1]: Starting process with command `java -Dserver.port=27231 $JAVA_OPTS -jar build/libs/*.jar`
2021-10-11T15:08:01.700376+00:00 app[web.1]: Create a Procfile to customize the command used to run this process: https://devcenter.heroku.com/articles/procfile
2021-10-11T15:08:01.714768+00:00 app[web.1]: Setting JAVA_TOOL_OPTIONS defaults based on dyno size. Custom settings will override them.
2021-10-11T15:08:01.718174+00:00 app[web.1]: Picked up JAVA_TOOL_OPTIONS: -Xmx300m -Xss512k -XX:CICompilerCount=2 -Dfile.encoding=UTF-8 
2021-10-11T15:08:01.791783+00:00 app[web.1]: Exception in thread "main" java.lang.NoClassDefFoundError: org/springframework/boot/SpringApplication
2021-10-11T15:08:01.791846+00:00 app[web.1]: 	at aplicacaoBridge.MainKt.main(main.kt:12)
2021-10-11T15:08:01.791876+00:00 app[web.1]: 	at aplicacaoBridge.MainKt.main(main.kt)
2021-10-11T15:08:01.791932+00:00 app[web.1]: Caused by: java.lang.ClassNotFoundException: org.springframework.boot.SpringApplication
2021-10-11T15:08:01.791957+00:00 app[web.1]: 	at java.net.URLClassLoader.findClass(URLClassLoader.java:382)
2021-10-11T15:08:01.791984+00:00 app[web.1]: 	at java.lang.ClassLoader.loadClass(ClassLoader.java:418)
2021-10-11T15:08:01.792019+00:00 app[web.1]: 	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:352)
2021-10-11T15:08:01.792034+00:00 app[web.1]: 	at java.lang.ClassLoader.loadClass(ClassLoader.java:351)
2021-10-11T15:08:01.792063+00:00 app[web.1]: 	... 2 more
2021-10-11T15:08:01.944005+00:00 heroku[web.1]: Process exited with status 1
2021-10-11T15:08:02.058667+00:00 heroku[web.1]: State changed from starting to crashed
```

## O algoritimo

Para desenvolver o algoritimo que encontre o menor número múltiplo duodigito de uma entrada **X** comecei utilizando uma linguagem de programação mais familiar para 
mim dado que ainda não havia entrado em contato com a linguagem *Kotlin*. Utilizei python, e depois de testadas as lógicas e verificações, traduzi meu algorítimo para
compor o único controlador da API. 

O código python que baseou minha solução é o seguinte: 

```python
def temSomenteDois(string):
  ## percorrer os números
  numerosConhecidos = []
  for number in string: 
    if number not in numerosConhecidos:
      numerosConhecidos.append(number)

  return len(numerosConhecidos) == 2

def encontrarProximoMultiplo(numero, indice):
  print(f'-------------> Próximo multiplo: {numero*indice} ------- X{indice}')
  return numero*indice

def encontraDuo(numero, limite=20):
  menorMultiploDuodigito = 0
  proximo = 0

  indice = 0
  while (indice < limite and not menorMultiploDuodigito):
    ## incremento
    indice += 1

    ## encontra o proximo multiplo, 
    # partindo do  próprio número
    proximo = encontrarProximoMultiplo(numero, indice)

    ## checa se é um duodigito
    tem = temSomenteDois(str(proximo))

    ## se for duo, guarda
    if (tem):
      menorMultiploDuodigito = proximo
      break

  if (menorMultiploDuodigito == 0):
    print('----------> Não foi possível encontrar <-----------')
  else: 
    print(f'O MENOR DUODIGITO MULTIPLO DE {numero} É {menorMultiploDuodigito}, FORAM NECESSÁRIAS {indice} ITERAÇÕES')
    return numero



## X=999, duo=9990
encontraDuo(999)

## X=123, duo=3444
encontraDuo(123, 200)
```

## Rodando local

Basicamente é necessário que você clone o repositório atual, e abra-o no IntelliJ. Aguarde/execute o *build*, em seguinda abra o arquivo `src/kotlin/main.kt`,
e execute a função principal (**main()**) clicando no botão de *play* verde que irá aparecer.

## Requisições

A API espera requisições no endereço `"/duodigitos/?numero={seu-numero}"` e o retorno da requisição seguirá o seguinte formato: 

```json
{
  "encontrado": Boolean, 
  "tempoDeProcessamento": Long,
  "menorDuodigito": Int,
  "numeroDeIteracoes": Int
}
```
