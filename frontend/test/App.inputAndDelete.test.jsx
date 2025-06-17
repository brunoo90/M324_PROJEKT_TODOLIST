import App from "../src/App";
import { render, screen, waitFor, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';

beforeEach(() => {
  fetch.resetMocks();
});

test('calls DELETE fetch when delete button is clicked', async () => {
  // Mock für initiales Laden der Todos
  fetch.mockResponseOnce(JSON.stringify([
    { id: 1, taskdescription: 'Task zum Löschen' },
  ]));

  render(<App />);

  // Warte, bis die Aufgabe gerendert ist
  const deleteButton = await screen.findByRole('button', { name: '✓' });
  expect(deleteButton).toBeInTheDocument();

  // Mock für DELETE Anfrage
  fetch.mockResponseOnce('', { status: 200 });

  // Klick auf den Delete Button
  fireEvent.click(deleteButton);

  await waitFor(() => {
    // Prüfe, ob fetch mit Methode DELETE aufgerufen wurde
    expect(fetch).toHaveBeenCalledWith(
      'http://localhost:8080/task/1',
      expect.objectContaining({
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' },
      })
    );
  });
});
