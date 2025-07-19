#!/usr/bin/python3
import random
from music21 import *


# Define constants
NUM_BARS = 8  # Number of bars per chromosome
POPULATION_SIZE = 10  # Number of individuals in the population
MUTATION_RATE = 0.1  # Probability of mutation
NUM_GENERATIONS = 20  # Number of generations
NOTES = ['C4', 'D4', 'E4', 'F4', 'G4', 'A4', 'B4', 'C5']  # Available notes
CHORDS = [
    ['C4', 'E4', 'G4'],  # C major
    ['G3', 'B3', 'D4'],  # G major
    ['A3', 'C4', 'E4'],  # A minor
    ['F3', 'A3', 'C4'],  # F major
    ['E3', 'G3', 'B3'],  # E minor
    ['D3', 'F3', 'A3'],  # D minor
]  # Available chords as note collections
NOTE_DURATIONS = [0.25, 0.5, 1.0]  # Note durations: sixteenth, eighth, quarter
CHORD_DURATIONS = [2.0, 4.0]  # Chord durations: half note, whole note


# Helper function to generate a random note with varied duration
def random_note():
    n = note.Note(random.choice(NOTES))
    n.duration = duration.Duration(random.choice(NOTE_DURATIONS))
    return n


# Helper function to generate a random chord with fixed duration (full or half measure)
def random_chord():
    chord_notes = random.choice(CHORDS)
    c = chord.Chord(chord_notes)
    c.duration = duration.Duration(random.choice(CHORD_DURATIONS))
    return c


# Generate a random measure (bar) consisting of melody and chords
def generate_random_measure():
    measure = stream.Measure()

    # Add the chord to the measure
    harmony = random_chord()
    measure.append(harmony)

    # Fill the measure with melody notes independently
    melody_duration = 0
    while melody_duration < 4.0:  # Assume 4/4 time signature
        next_note = random_note()
        remaining_duration = 4.0 - melody_duration
        if next_note.duration.quarterLength > remaining_duration:
            next_note.duration.quarterLength = remaining_duration
        measure.append(next_note)
        melody_duration += next_note.duration.quarterLength

    return measure


# Generate a random chromosome
def generate_chromosome():
    return [generate_random_measure() for _ in range(NUM_BARS)]


# Fitness function to evaluate a chromosome (same as before)
def fitness_function(chromosome):
    fitness = 0

    # Reward variety of unique notes (encourages melodic diversity)
    unique_notes = set()
    for measure in chromosome:
        for element in measure:
            if isinstance(element, note.Note):
                unique_notes.add(element.name)
    fitness += len(unique_notes)

    # Check if the melody ends on the root note of the last chord
    last_measure = chromosome[-1]
    if len(last_measure.notesAndRests) >= 2:
        melody_note = last_measure.notesAndRests[0]
        final_chord = last_measure.notesAndRests[1]
        if isinstance(melody_note, note.Note) and isinstance(final_chord, chord.Chord):
            if melody_note.name == final_chord.root().name:
                fitness += 10  # Reward for melody resolution

    # Check for cadence (G to C, or V to I)
    if len(chromosome) >= 2:
        penultimate_chord = None
        last_chord = None

        # Extract chords from the last two measures
        if len(chromosome[-2].notesAndRests) >= 2:
            penultimate_chord = chromosome[-2].notesAndRests[1]
        if len(chromosome[-1].notesAndRests) >= 2:
            last_chord = chromosome[-1].notesAndRests[1]

        # Check for cadence
        if isinstance(penultimate_chord, chord.Chord) and isinstance(last_chord, chord.Chord):
            if penultimate_chord.root().name == 'G' and last_chord.root().name == 'C':
                fitness += 20  # Reward for V-I cadence

    # Reward ending with the same chord as the starting chord
    first_chord = chromosome[0].notesAndRests[1] if len(chromosome[0].notesAndRests) >= 2 else None
    last_chord = chromosome[-1].notesAndRests[1] if len(chromosome[-1].notesAndRests) >= 2 else None

    if isinstance(first_chord, chord.Chord) and isinstance(last_chord, chord.Chord):
        if first_chord.root().name == last_chord.root().name:
            fitness += 15  # Reward for structural coherence

    return fitness


# Crossover between two chromosomes
def crossover(parent1, parent2):
    split = random.randint(1, NUM_BARS - 1)
    child = parent1[:split] + parent2[split:]
    return child


# Mutate a chromosome
def mutate(chromosome):
    for i in range(NUM_BARS):
        if random.random() < MUTATION_RATE:
            chromosome[i] = generate_random_measure()
    return chromosome


# Genetic algorithm
def genetic_algorithm():
    # Initialize population
    population = [generate_chromosome() for _ in range(POPULATION_SIZE)]

    for generation in range(NUM_GENERATIONS):
        # Evaluate fitness
        fitness_scores = [(chromosome, fitness_function(chromosome)) for chromosome in population]
        fitness_scores.sort(key=lambda x: x[1], reverse=True)

        # Select the top individuals
        top_individuals = [x[0] for x in fitness_scores[:POPULATION_SIZE // 2]]

        # Create the next generation
        next_generation = top_individuals.copy()
        while len(next_generation) < POPULATION_SIZE:
            parent1, parent2 = random.sample(top_individuals, 2)
            child = crossover(parent1, parent2)
            child = mutate(child)
            next_generation.append(child)

        population = next_generation
        print(f"Generation {generation + 1}: Best fitness = {fitness_scores[0][1]}")

    # Return the best individual
    best_chromosome = max(population, key=fitness_function)
    return best_chromosome


# Convert a chromosome to a music21 stream.Score for playback and visualization
def chromosome_to_score(chromosome):
    score = stream.Score()
    for measure in chromosome:
        score.append(measure)
    return score


# Run the genetic algorithm and play the resulting music
best_music = genetic_algorithm()
score = chromosome_to_score(best_music)
score.show('text')  # Display the score as text
sp = midi.realtime.StreamPlayer(score)
sp.play()