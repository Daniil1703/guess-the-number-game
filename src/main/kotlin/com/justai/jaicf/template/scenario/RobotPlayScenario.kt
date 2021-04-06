package com.justai.jaicf.template.scenario

import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.model.scenario.getValue

object RobotPlayScenario : Scenario {

    const val playRobot = "/playRobot"

    override val scenario by Scenario {
        state(playRobot) {
            action {
                reactions.say("Я начинаю!")
            }
        }
    }

}