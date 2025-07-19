#!/usr/bin/python3
from music21 import *
import numpy as np

def init_automaton(rows, cols):
        return np.random.randint(0,2,size=(rows,cols))

def init_glider(rows, cols):
    grid = np.zeros((rows,cols), dtype=int)
    glider = [(0,1),(1,2),(2,0),(2,1),(2,2)]

    for r,c in glider:
        if r<rows and c<cols:
            grid[r][c] = 1
    return grid


def evolve_automaton(ca, steps):
    generations = [ca]
    rows, cols = ca.shape
    for step in range(steps):
        new_ca = np.zeros((rows,cols), dtype=int)
        for c in range(rows):
            for r in range(cols):
                neighbour_sum = (
                    ca[(r - 1) % rows, (c - 1) % cols] +
                    ca[(r - 1) % rows, c] +
                    ca[(r - 1) % rows, (c + 1) % cols] +
                    ca[ r, (c - 1) % cols] +
                    ca[ r, (c + 1) % cols] +
                    ca[(r + 1) % rows, (c - 1) % cols] +
                    ca[(r + 1) % rows, c ] +
                    ca[(r + 1) % rows, (c + 1) % cols]
                )
                if ca[r,c] == 1:
                    new_ca[r,c] = 1
                    if (neighbour_sum < 2 or neighbour_sum > 3): new_ca[r,c] = 0
                else:
                    if (neighbour_sum == 3): new_ca[r,c] = 1

        ca=new_ca
        generations.append(ca)
    return generations


def ca_to_music(ca):
    s = stream.Stream()
    s.append(tempo.MetronomeMark(number=120))
    rows, cols = ca.shape

    pitch_set = ['C4', 'D4', 'E4', 'F4', 'G4', 'A4', 'B4']
    duration_set = [0.25, 0.5, 1.0, 1.5, 2]

    for r in range(rows):
            for c in range(cols):
                if ca[r,c] == 1:
                    pitch = pitch_set[(r+c) % len(pitch_set)]
                    duration = duration_set[(r+c) % len(duration_set)]
                    n=note.Note(pitch)
                    n.quarterLength=duration
                    s.append(n)
    return s

def print_automaton(ca):
    print("Automaton:")
    for row in ca:
            print(''.join('*' if cell == 1 else '.' for cell in row))
    print()


rows=5
cols=5
steps=2
ca = init_glider(rows, cols)
print_automaton(ca)

generations = evolve_automaton(ca, steps)

for generation in generations:
    music_stream = ca_to_music(generation)
    print_automaton(generation)
    music_stream.show('text')
    sp = midi.realtime.StreamPlayer(music_stream)
    sp.play()


