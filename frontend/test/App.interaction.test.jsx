import App from "../src/App";
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import fetchMock from 'jest-fetch-mock';

fetchMock.enableMocks();

beforeEach(() => {
  fetch.resetMocks();
});

test('calls DELETE fetch when delete button is clicked', async () => {
  fetch.mockResponseOnce(JSON.stringify([
    { id: 1, taskdescription: 'Task zum Löschen' }
  ]));

  render(<App />);

  const deleteButton = await screen.findByRole('button', { name: '✔' });
  expect(deleteButton).toBeInTheDocument();

  fetch.mockResponseOnce('', { status: 200 });

  fireEvent.click(deleteButton);

  await waitFor(() => {
    expect(fetch).toHaveBeenCalledWith(
      'http://localhost:8080/task/1',
      expect.objectContaining({
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' }
      })
    );
  });
});
