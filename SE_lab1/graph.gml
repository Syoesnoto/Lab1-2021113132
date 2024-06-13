graph [
	ui.stylesheet "node { size: 20px; fill-color: gray; text-size: 20px; text-color: black;}edge { size: 3px; text-size: 20px; text-color: black; arrow-size: 10px, 4px; arrow-shape: arrow;}"
	node [
		id "sky"
		ui.label "sky"
	]
	node [
		id "a"
		ui.label "a"
	]
	node [
		id "twinkle"
		ui.label "twinkle"
	]
	node [
		id "star"
		ui.label "star"
	]
	node [
		id "like"
		ui.label "like"
	]
	node [
		id "in"
		ui.label "in"
	]
	node [
		id "i"
		ui.label "i"
	]
	node [
		id "the"
		ui.label "the"
	]
	node [
		id "how"
		ui.label "how"
	]
	node [
		id "high"
		ui.label "high"
	]
	node [
		id "diamond"
		ui.label "diamond"
	]
	node [
		id "what"
		ui.label "what"
	]
	node [
		id "world"
		ui.label "world"
	]
	node [
		id "are"
		ui.label "are"
	]
	node [
		id "above"
		ui.label "above"
	]
	node [
		id "wonder"
		ui.label "wonder"
	]
	node [
		id "up"
		ui.label "up"
	]
	node [
		id "so"
		ui.label "so"
	]
	node [
		id "you"
		ui.label "you"
	]
	node [
		id "little"
		ui.label "little"
	]
	edge [
		id "sky --> twinkle"
		source "sky"
		target "twinkle"
		ui.label 1
		directed 1
	]
	edge [
		id "a --> diamond"
		source "a"
		target "diamond"
		ui.label 1
		directed 1
	]
	edge [
		id "twinkle --> little"
		source "twinkle"
		target "little"
		ui.label 6
		directed 1
	]
	edge [
		id "star --> how"
		source "star"
		target "how"
		ui.label 4
		directed 1
	]
	edge [
		id "star --> twinkle"
		source "star"
		target "twinkle"
		ui.label 2
		directed 1
	]
	edge [
		id "like --> a"
		source "like"
		target "a"
		ui.label 1
		directed 1
	]
	edge [
		id "in --> the"
		source "in"
		target "the"
		ui.label 1
		directed 1
	]
	edge [
		id "i --> wonder"
		source "i"
		target "wonder"
		ui.label 3
		directed 1
	]
	edge [
		id "the --> sky"
		source "the"
		target "sky"
		ui.label 1
		directed 1
	]
	edge [
		id "the --> world"
		source "the"
		target "world"
		ui.label 1
		directed 1
	]
	edge [
		id "how --> you"
		source "how"
		target "you"
		ui.label 1
		directed 1
	]
	edge [
		id "how --> i"
		source "how"
		target "i"
		ui.label 3
		directed 1
	]
	edge [
		id "high --> like"
		source "high"
		target "like"
		ui.label 1
		directed 1
	]
	edge [
		id "diamond --> in"
		source "diamond"
		target "in"
		ui.label 1
		directed 1
	]
	edge [
		id "what --> you"
		source "what"
		target "you"
		ui.label 4
		directed 1
	]
	edge [
		id "world --> so"
		source "world"
		target "so"
		ui.label 1
		directed 1
	]
	edge [
		id "are --> twinkle"
		source "are"
		target "twinkle"
		ui.label 2
		directed 1
	]
	edge [
		id "are --> up"
		source "are"
		target "up"
		ui.label 1
		directed 1
	]
	edge [
		id "above --> the"
		source "above"
		target "the"
		ui.label 1
		directed 1
	]
	edge [
		id "wonder --> what"
		source "wonder"
		target "what"
		ui.label 4
		directed 1
	]
	edge [
		id "up --> above"
		source "up"
		target "above"
		ui.label 1
		directed 1
	]
	edge [
		id "so --> high"
		source "so"
		target "high"
		ui.label 1
		directed 1
	]
	edge [
		id "you --> wonder"
		source "you"
		target "wonder"
		ui.label 1
		directed 1
	]
	edge [
		id "you --> are"
		source "you"
		target "are"
		ui.label 4
		directed 1
	]
	edge [
		id "little --> star"
		source "little"
		target "star"
		ui.label 6
		directed 1
	]
]
