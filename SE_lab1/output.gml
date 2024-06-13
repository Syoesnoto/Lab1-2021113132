graph [
	node [
		id "new"
		ui.label "new"
		ui.style "text-size: 30px; text-color: black;"
	]
	node [
		id "worlds"
		ui.label "worlds"
		ui.style "text-size: 30px; text-color: black;"
	]
	node [
		id "explore"
		ui.label "explore"
		ui.style "text-size: 30px; text-color: black;"
	]
	node [
		id "and"
		ui.label "and"
		ui.style "text-size: 30px; text-color: black;"
	]
	node [
		id "to"
		ui.label "to"
		ui.style "text-size: 30px; text-color: black;"
	]
	node [
		id "civilizations"
		ui.label "civilizations"
		ui.style "text-size: 30px; text-color: black;"
	]
	node [
		id "seek"
		ui.label "seek"
		ui.style "text-size: 30px; text-color: black;"
	]
	node [
		id "strange"
		ui.label "strange"
		ui.style "text-size: 30px; text-color: black;"
	]
	node [
		id "life"
		ui.label "life"
		ui.style "text-size: 30px; text-color: black;"
	]
	node [
		id "out"
		ui.label "out"
		ui.style "text-size: 30px; text-color: black;"
	]
	edge [
		id "new --> civilizations"
		source "new"
		target "civilizations"
		ui.label 1
		ui.style "text-size: 30px; text-color: black;"
	]
	edge [
		id "new --> life"
		source "new"
		target "life"
		ui.label 1
		ui.style "text-size: 30px; text-color: black;"
	]
	edge [
		id "new --> worlds"
		source "new"
		target "worlds"
		ui.label 1
		ui.style "text-size: 30px; text-color: black;"
	]
	edge [
		id "worlds --> to"
		source "worlds"
		target "to"
		ui.label 1
		ui.style "text-size: 30px; text-color: black;"
	]
	edge [
		id "explore --> strange"
		source "explore"
		target "strange"
		ui.label 1
		ui.style "text-size: 30px; text-color: black;"
	]
	edge [
		id "and --> new"
		source "and"
		target "new"
		ui.label 1
		ui.style "text-size: 30px; text-color: black;"
	]
	edge [
		id "to --> seek"
		source "to"
		target "seek"
		ui.label 1
		ui.style "text-size: 30px; text-color: black;"
	]
	edge [
		id "to --> explore"
		source "to"
		target "explore"
		ui.label 1
		ui.style "text-size: 30px; text-color: black;"
	]
	edge [
		id "seek --> out"
		source "seek"
		target "out"
		ui.label 1
		ui.style "text-size: 30px; text-color: black;"
	]
	edge [
		id "strange --> new"
		source "strange"
		target "new"
		ui.label 1
		ui.style "text-size: 30px; text-color: black;"
	]
	edge [
		id "life --> and"
		source "life"
		target "and"
		ui.label 1
		ui.style "text-size: 30px; text-color: black;"
	]
	edge [
		id "out --> new"
		source "out"
		target "new"
		ui.label 1
		ui.style "text-size: 30px; text-color: black;"
	]
]
