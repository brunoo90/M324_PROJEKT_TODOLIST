import logo from './logo.svg';
import './App.css';
import React from 'react';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      todos: typeof props.todos === 'undefined' ? [] : props.todos,
      taskdescription: ""
    };
  }

  handleChange = event => {
    this.setState({ taskdescription: event.target.value });
  }

  handleSubmit = event => {
    event.preventDefault();
    fetch("http://localhost:8080/tasks", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ taskdescription: this.state.taskdescription })
    })
    .then(response => {
      this.setState({
        todos: [...this.state.todos, { taskdescription: this.state.taskdescription }],
        taskdescription: ""
      });
    })
    .catch(error => console.log(error));
  }

  componentDidMount() {
    fetch("http://localhost:8080")
      .then(response => response.json())
      .then(data => this.setState({ todos: data }))
      .catch(error => console.log(error));
  }

  handleClick = taskdescription => {
    fetch("http://localhost:8080/delete", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ taskdescription })
    })
    .then(() => window.location.href = "/")
    .catch(error => console.log(error));
  }

  renderTasks(todos) {
    return (
      <ul className="todo-list">
        {todos.map((todo, index) => (
          <li key={todo.taskdescription} className="todo-item">
            {`Task ${index + 1}: ${todo.taskdescription}`}
            <button onClick={() => this.handleClick(todo.taskdescription)} className="done-btn">✔️</button>
          </li>
        ))}
      </ul>
    );
  }

  render() {
    return (
      <div className="App">
        <header className="App-header">
          <div className="top-bar">
            <img src={logo} className="App-logo" alt="logo" />
            <h1>ToDo Liste</h1>
          </div>

          <form onSubmit={this.handleSubmit} className="todo-input-container">
            <input
              type="text"
              value={this.state.taskdescription}
              onChange={this.handleChange}
              placeholder="Neues ToDo eingeben..."
              className="todo-input"
            />
            <button type="submit" className="submit-btn">➕</button>
          </form>

          <div className="task-list">
            {this.renderTasks(this.state.todos)}
          </div>
        </header>
      </div>
    );
  }
}

export default App;
