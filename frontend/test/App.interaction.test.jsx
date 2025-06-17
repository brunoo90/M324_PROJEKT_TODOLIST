import App from "../src/App";
import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';

test('updates input field value when user types', () => {
  render(<App />);
  const input = screen.getByLabelText(/neues todo anlegen/i);

  // Check that the input field is of type text
  expect(input).toHaveAttribute('type', 'text');

  // Anfangs soll das Eingabefeld leer sein
  expect(input.value).toBe('');

  // Simuliere Nutzereingabe
  fireEvent.change(input, { target: { value: 'Neue Aufgabe' } });

  // Überprüfe, ob der Wert aktualisiert wurde
  expect(input.value).toBe('Neue Aufgabe');
});
