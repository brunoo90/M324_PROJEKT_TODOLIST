import App from "../src/App";
import { render, screen, waitFor, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';

beforeEach(() => {
  fetch.resetMocks();
});

test('calls DELETE fetch when delete button is clicked', async () => {
  // 🟢 Mock initiales Laden mit einem Todo
  fetch.mockResponseOnce(JSON.stringify([
    { id: 1, taskdescription: 'Task zum Löschen' },
  ]));

  render(<App />);

  // 🟡 Suche nach "Task zum Löschen", dann hole den Button daneben
  const todoText = await screen.findByText(/Task zum Löschen/i);
  expect(todoText).toBeInTheDocument();

  // 🔍 Suche den Button im gleichen <li>
  const todoItem = todoText.closest('li');
  const deleteButton = todoItem?.querySelector('button');
  expect(deleteButton).toBeInTheDocument();

  // 🔄 Mock für DELETE-Request → leere, aber gültige Antwort
  fetch.mockResponseOnce('{}', { status: 200 });

  // 🚮 Klicke auf Delete
  fireEvent.click(deleteButton);

  await waitFor(() => {
    expect(fetch).toHaveBeenCalledWith(
      'http://localhost:8080/task/1',
      expect.objectContaining({
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' },
      })
    );
  });
});
