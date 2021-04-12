package com.justai.jaicf.template.scenario

import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.reactions.buttons

const val exitGame = "/exit"

val mainScenario = Scenario {

    append(HumanPlayScenario)
    append(RobotPlayScenario)

    state("start") {
        activators {
            regex("/start")
            intent("game")
        }
        action {
            reactions.run {
                image("https://media.giphy.com/media/l2JdV8tMQxD6OZf8s/giphy.gif")
                say("Привет! Это игра больше-меньше")
                say("Играем?")
                buttons("Да" to "./whoFirst", "Нет" to "/exit")
                buttons("Правила" to "/help")
            }
        }

        state("whoFirst") {
            action {
                reactions.run{
                    say("Кто начнет?")
                    buttons("Ты" to RobotPlayScenario.playRobot, "Я" to HumanPlayScenario.playHuman)
                }
            }
        }
    }

    state(exitGame) {
        activators {
            regex("/exit")
            regex("/quit")
            intent("exit")
        }
        action {
            reactions.run {
                say("Вот и поиграли, если захочешь еще, то скажи что-то вроде 'Хочу играть'")
            }
        }
    }

    state("help", noContext = true) {
        globalActivators {
            intent("rules")
        }

        action {
            reactions.run {
                say("Я или ты, загадываем любое число случайным образом и просим угадать это число.")
                say("Затем вводим число. Если предположение неверно, то надо сказать, больше " +
                        "или меньше загаданное число, а затем повторяем попытку.")
                say("Если число угадано, игра будет считаться завершенной.")
                say("Начнем?")
                buttons("Да" to "/start/whoFirst", "Нет" to exitGame)
            }
        }
    }

    fallback {
        reactions.run{
            sayRandom(
                "Прости, но я ничего не понял...",
                "Прости, не мог бы ты повторить?"
            )
            say("Если хочешь играть, то так и скажи!")
        }
    }
}