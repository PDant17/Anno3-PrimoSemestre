#!/usr/bin/python3
import random
from music21 import * 

# Mapping vowels to possible notes
vowel_to_notes = {
    'a': ['G3', 'E4', 'C5', 'A5'],
    'e': ['A3', 'F4', 'D5'],
    'i': ['B3', 'G4', 'E5'],
    'o': ['C4', 'A4', 'F5'],
    'u': ['D4', 'B4', 'G5']
}

# Possible note durations
possible_durations = [0.25, 0.5, 1.0, 1.5, 2.0]

# Mapping note roots to chords
note_to_chord = {
    'C': ['C4', 'E4', 'G4'],
    'D': ['D4', 'F4', 'A4'],
    'E': ['E4', 'G4', 'B4'],
    'F': ['F4', 'A4', 'C5'],
    'G': ['G4', 'B4', 'D5'],
    'A': ['A4', 'C5', 'E5'],
    'B': ['B4', 'D5', 'F5']
}

def create_melody_from_string(input_string):
    # Extract vowels from the string
    vowels = [char.lower() for char in input_string if char.lower() in vowel_to_notes]

    # Create a stream for the melody
    melody = stream.Stream()
    melody.append(meter.TimeSignature('4/4'))  # Set a 4/4 time signature

    # Initialize the previous note's MIDI pitch (None at the start)
    previous_pitch = None
    current_measure = stream.Measure()
    current_beat_count = 0

    # Generate notes based on vowels
    print(f"vowels: {vowels}")
    for vowel in vowels:
        print(f"vowel: {vowel}")
        # Choose a random note for the vowel, keeping it within an octave of the previous note
        while True:
            chosen_pitch = random.choice(vowel_to_notes[vowel])
            chosen_midi = note.Note(chosen_pitch).pitch.midi
            if previous_pitch is None or abs(chosen_midi - previous_pitch) <= 12:
                break

        # Set the current pitch as the previous pitch for the next iteration
        previous_pitch = chosen_midi

        # Choose a random duration for the note
        note_duration = random.choice(possible_durations)
        if current_beat_count + note_duration > 4:
            # Finalize the current measure and add it to the melody
            melody.append(current_measure)
            current_measure = stream.Measure()
            current_beat_count = 0

        # Create a music21 Note object
        melody_note = note.Note(chosen_pitch)
        melody_note.duration = duration.Duration(note_duration)
        print(f"Adding... note {melody_note}")
        current_measure.append(melody_note)
        current_beat_count += note_duration

        # Add a chord at the start of a new measure
        if current_beat_count == note_duration:
            note_root = melody_note.pitch.name[0]  # Get the root of the chord
            if note_root in note_to_chord:
                chord_notes = note_to_chord[note_root]
                measure_chord = chord.Chord(chord_notes)
                print(f"Adding... chord {measure_chord}")
                current_measure.insert(0, measure_chord)

    # Append the last measure if it contains any notes
    if len(current_measure.notes) > 0:
        melody.append(current_measure)

    return melody

def main():
    input_string = input("Enter a string to generate a melody: ")
    melody = create_melody_from_string(input_string)
    print("Playing the generated melody...")
    # Use the StreamPlayer to play the melody
    player = midi.realtime.StreamPlayer(melody)
    player.play()

if __name__ == "__main__":
    main()