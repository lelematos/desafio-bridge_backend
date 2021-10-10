package aplicacaoBridge.controladores

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


fun temSomenteDoisDigitos(numero: String): Boolean {
    val numerosConhecidos: MutableList<String> = arrayOf<String>().toMutableList()
    for (digito in numero) {
        if (!numerosConhecidos.contains(digito.toString())){
            numerosConhecidos.add(digito.toString())
        }
    }
    return numerosConhecidos.size == 2
}

fun proximoMultiploDeX(numero: Int, indice: Int): Int {
    return numero*indice
}

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:3000"])
@RestController
@RequestMapping("/duodigitos")
class ControladorDuodigitos {
    @GetMapping("/")
    fun encontrarMenorMultiploDuodigito (numero: Int, limite: Int?): MenorMultiploDuodigito {
        // registrando o tempo de inicio
        val start = System.currentTimeMillis()

        // definindo um valor padrão para o limite de iterações
        val limite = limite ?: 40

        // inicializando as variáveis
        var menorMultiploDuodigito: Int = 0
        var proximo: Int = 0
        var indice: Int = 0

        while ((indice < limite) && (menorMultiploDuodigito == 0)){
            // incremento
            indice = indice + 1

            // encontrando o proximo multiplo (partindo do próprio número, número*1)
            proximo = proximoMultiploDeX(numero, indice)

            // verificando se é um duodigito
            val tem: Boolean = temSomenteDoisDigitos(proximo.toString())

            // se for duodigito, garantimos que será o menor, assim podemos guardá-lo
            if (tem) {
                menorMultiploDuodigito = proximo
                break
            }
        }

        if (menorMultiploDuodigito == 0) {
            return MenorMultiploDuodigito(
                false,
                System.currentTimeMillis() - start, null,
                indice
            )
        }
        return MenorMultiploDuodigito(
            true,
            System.currentTimeMillis() - start,
            menorMultiploDuodigito,
            indice
        )
    }
}

data class MenorMultiploDuodigito(
    val encontrado: Boolean,
    val tempoDeProcessamento: Long?,
    val menorDuodigito: Int?,
    val numeroDeIteracoes: Int?
    )