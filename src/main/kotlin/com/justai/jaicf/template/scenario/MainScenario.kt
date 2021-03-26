package com.justai.jaicf.template.scenario

import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.reactions.buttons

val mainScenario = Scenario {
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
                    buttons(Pair("Ты", "you"), Pair("Я", "me"))
                }
            }

            state("you") {
                action {
                    reactions.run {
                        say("Я начинаю!")
                    }
                }
            }

            state("me") {
                action {
                    reactions.run {
                        say("Ты начинаешь!")
                    }
                }
            }
        }
    }

    state("exit") {
        activators {
            regex("/exit")
            regex("/quit")
            intent("exit")
        }
        action {
            reactions.run {
                say("Очень жаль! Ну ничего страшного, если захочешь поиграть, то скажи что-то вроде 'Хочу играть'")
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
                buttons("Да" to "/start/whoFirst", "Нет" to "/exit")
            }
        }
    }

    fallback {
        reactions.run{
            say("You said ${request.input}.")
            sayRandom(
                "Прости, я ничего не понял...",
                "Прости, не мог бы ты повторить?"
            )
        }
    }
}