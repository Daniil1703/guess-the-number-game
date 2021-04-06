package com.justai.jaicf.template.scenario

import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.model.scenario.getValue

object HumanPlayScenario : Scenario {

    const val playHuman = "/playHuman"

    override val scenario by Scenario {
        state(playHuman) {
            action {
                reactions.say("Ты начинаешь!")
            }
        }
    }
}