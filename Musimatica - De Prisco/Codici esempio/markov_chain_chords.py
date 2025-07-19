#!/usr/bin/python3
import random
from music21 import stream, chord, note, meter, midi

# Define a Markov chain for chord progressions
chord_markov_chain = {
    'C': {'G': 0.4, 'F': 0.3, 'Am': 0.3},
    'G': {'C': 0.5, 'Em': 0.3, 'D': 0.2},
    'F': {'C': 0.4, 'G': 0.3, 'Dm': 0.3},
    'Am': {'F': 0.4, 'G': 0.3, 'C': 0.3},
    'Em': {'G': 0.4, 'C': 0.4, 'Am': 0.2},
    'D': {'G': 0.6, 'Em': 0.4},
    'Dm': {'F': 0.5, 'C': 0.5},
}

chord_map = {
    'C': ['C4', 'E4', 'G4'],  # C major
    'G': ['G3', 'B3', 'D4'],  # G major
    'F': ['F3', 'A3', 'C4'],  # F major
    'Am': ['A3', 'C4', 'E4'], # A minor
    'Em': ['E3', 'G3', 'B3'], # E minor
    'D': ['D3', 'F#3', 'A3'], # D major
    'Dm': ['D3', 'F3', 'A3'], # D minor
}

# Define a mapping from chord names to arbitrary melody note lists and probabilities
melody_map = {
    'C': (['C4', 'D4', 'E4', 'G4'], [0.2, 0.1, 0.3, 0.2]),  # Notes from the C major scale
    'G': (['G3', 'E3', 'B3', 'D4', 'F4'], [0.2, 0.2, 0.3, 0.2, 0.1]),  # Notes from the G mixolydian scale
    'F': (['F3', 'G3', 'A3', 'C4', 'D4'], [0.3, 0.2, 0.2, 0.2, 0.1]),  # Notes from the F major scale
    'Am': (['A3', 'B3', 'C4', 'E4', 'G4'], [0.2, 0.2, 0.3, 0.2, 0.1]), # Notes from the A minor scale
    'Em': (['E3', 'F#3', 'G3', 'B3', 'D4'], [0.2, 0.3, 0.2, 0.2, 0.1]), # Notes from the E minor scale
    'D': (['D3', 'E3', 'F#3', 'A3', 'B3'], [0.2, 0.2, 0.3, 0.2, 0.1]), # Notes from the D major scale
    'Dm': (['D3', 'E3', 'F3', 'A3', 'C4'], [0.3, 0.2, 0.2, 0.2, 0.1]), # Notes from the D minor scale
}

# Define pre-established duration patterns
duration_patterns = [
    [1, 1, 1, 1],      # Four quarter notes
    [1, 0.5, 0.5, 1, 1],  # Two eighth notes followed by two quarter notes
    [1.5, 0.5, 1, 1],  # Dotted quarter, eighth, and two quarters
    [0.25, 0.25, 1.5, 1],  # Two sixteenths, dotted quarter, and a quarter
    [0.25, 0.25, 0.25, 0.25, 1, 0.5, 0.5, 1]
]

def choose_next_chord(current_chord):
    """Choose the next chord based on the Markov chain."""
    transitions = chord_markov_chain.get(current_chord, {})
    if not transitions:
        # If no transitions are defined for the current chord, pick a random starting chord
        return random.choice(list(chord_markov_chain.keys()))
    chords = list(transitions.keys())
    probabilities = list(transitions.values())
    return random.choices(chords, probabilities)[0]

def generate_melody_for_chord(chord_name, pattern):
    """Generate melody notes for a given chord and duration pattern."""
    melody_notes = []
    melody_pitches, probabilities = melody_map[chord_name]
    for duration in pattern:
        pitch = random.choices(melody_pitches, probabilities)[0]  # Choose a pitch based on probabilities
        melody_notes.append(note.Note(pitch, quarterLength=duration))
    return melody_notes

def add_measure(current_chord):
    """Generate a single measure with a chord and melody."""
    # Choose the next chord
    next_chord = choose_next_chord(current_chord)
    harmony_chord = chord.Chord(chord_map[next_chord])  # Use the chord pitches
    harmony_chord.quarterLength = 4  # Make the chord span the measure

    # Select a duration pattern and generate melody notes
    pattern = random.choice(duration_patterns)
    melody_notes = generate_melody_for_chord(next_chord, pattern)

    # Create a measure and add the chord and melody notes
    measure = stream.Measure()
    measure.append(harmony_chord)  # Add the chord at the start of the measure

    # Explicitly set offsets for melody notes
    melody_offset = 0.0  # Start melody at the beginning of the measure
    for melody_note in melody_notes:
        melody_note.offset = melody_offset  # Explicitly set the offset
        measure.insert(melody_offset, melody_note)  # Insert the note at the correct position
        melody_offset += melody_note.quarterLength  # Update offset based on the note's duration

    return measure, next_chord


def add_last_measure(chord_name):
    """Generate the final measure with a chord and melody, ensuring the melody ends on the root note."""
    # Create the chord
    harmony_chord = chord.Chord(chord_map[chord_name])  # Use the chord pitches
    harmony_chord.quarterLength = 4  # Make the chord span the measure

    # Select a duration pattern and generate melody notes
    pattern = random.choice(duration_patterns)
    melody_notes = generate_melody_for_chord(chord_name, pattern)

    # Ensure the last melody note is the root of the chord
    root_note = chord_map[chord_name][0]  # The root note is the first note in the chord map
    melody_duration_used = sum(note.quarterLength for note in melody_notes[:-1])  # Duration without the last note
    last_note_duration = 4 - melody_duration_used  # Remaining duration to fill the measure

    # If thereâ€™s time left, add the root note as the last melody note
    if last_note_duration > 0:
        melody_notes[-1] = note.Note(root_note, quarterLength=last_note_duration)

    # Create a measure and add the chord and melody notes
    measure = stream.Measure()
    measure.append(harmony_chord)  # Add the chord at the start of the measure

    # Explicitly set offsets for melody notes
    melody_offset = 0.0  # Start melody at the beginning of the measure
    for melody_note in melody_notes:
        melody_note.offset = melody_offset  # Explicitly set the offset
        measure.insert(melody_offset, melody_note)  # Insert the note at the correct position
        melody_offset += melody_note.quarterLength  # Update offset based on the note's duration

    return measure


def generate_music(start_chord, num_measures):
    """Generate the complete music stream."""
    music_stream = stream.Stream()
    music_stream.append(meter.TimeSignature('4/4'))  # Add a 4/4 time signature

    current_chord = start_chord

    # Generate the main measures
    for _ in range(num_measures - 1):
        measure, current_chord = add_measure(current_chord)
        music_stream.append(measure)

    # Add the final measure with the initial chord
    measure = add_last_measure(start_chord)
    music_stream.append(measure)

    return music_stream

# Main program
if __name__ == "__main__":
    start_chord = 'C'  # Start with C major
    num_measures = 8  # Number of measures to generate
    music = generate_music(start_chord, num_measures)

    music.show('text')
    # Play the music using StreamPlayer
    sp = midi.realtime.StreamPlayer(music)
    print("Playing the music...")
    sp.play()