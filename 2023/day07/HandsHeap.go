package main

type HandsHeap []HandWithPower

func (h HandsHeap) Len() int      { return len(h) }
func (h HandsHeap) Swap(i, j int) { h[i], h[j] = h[j], h[i] }

func (h HandsHeap) Less(i, j int) bool {
	h1 := h[i]
	h2 := h[j]

	if h1.Power == h2.Power {
		for i := 0; i < HAND_CARDS; i++ {
			h1CurrentCard := rune(h1.Hand[i])
			h2CurrentCard := rune(h2.Hand[i])

			if h2.CardStrengthMap[h2CurrentCard] == h1.CardStrengthMap[h1CurrentCard] {
				continue
			}

			return h1.CardStrengthMap[h1CurrentCard] > h2.CardStrengthMap[h2CurrentCard]
		}
	}

	return h1.Power > h2.Power
}

func (h *HandsHeap) Push(x interface{}) {
	*h = append(*h, x.(HandWithPower))
}

func (h *HandsHeap) Pop() interface{} {
	old := *h
	n := len(old)
	x := old[n-1]
	*h = old[0 : n-1]
	return x
}
