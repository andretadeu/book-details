import React, {Component} from 'react'

class Form extends Component {
  constructor(props) {
    super(props);
    this.state = {isbn: null, bookTitle: null, pageCount: null, authors: []}

    this.handleChangeIsbn = this.handleChangeIsbn.bind(this);
    this.handleChangeTitle = this.handleChangeTitle.bind(this);
    this.handleChangePageCount = this.handleChangePageCount.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChangeIsbn(event) {
    this.setState({isbn: event.target.value, bookTitle: this.state.bookTitle, pageCount: this.state.pageCount});
  }

  handleChangeTitle(event) {
    this.setState({isbn: this.state.isbn, bookTitle: event.target.value, pageCount: this.state.pageCount});
  }

  handleChangePageCount(event) {
    this.setState({isbn: this.state.isbn, bookTitle: this.state.bookTitle, pageCount: event.target.value});
  }

  handleSubmit(event) {
    try {
      const response = fetch("http://localhost:8080/graphql", {
        method: 'POST',
        headers: {'Content-Type': 'application/json', 'Accept': 'application/json'},
        body: '{"query": "mutation BookInput($bIsbn: String!, $bTitle: String!, $bPageCount: Int!) ' +
            '{ insert(isbn: $bIsbn, title: $bTitle, pageCount: $bPageCount) }", "variables": ' +
            '{"bIsbn": "' + this.state.isbn + '", "bTitle": "' + this.state.bookTitle + '", "bPageCount": ' +
            this.state.pageCount + '}}',
      }).then(result => result.json())
          .then(result => this.setState({data: result}));
    } catch (e) {
      console.error(e);
    }
    event.preventDefault();
  }

  render() {
    return (
        <div className="Form">
          <form id="books" className="row" onSubmit={this.handleSubmit}>
            <div className="mb-3">
              <div className="col-2">
                <label htmlFor="isbn">ISBN-13</label>
              </div>
              <div>
                <input id="isbn" type="text" value={this.state.isbn || ''} onChange={this.handleChangeIsbn} />
              </div>
            </div>
            <div className="mb-3">
              <div className="col-2">
                <label htmlFor="title">Title</label>
              </div>
              <div>
                <input id="title" type="text" value={this.state.bookTitle || ''} onChange={this.handleChangeTitle}/>
              </div>
            </div>
            <div className="mb-3">
              <div className="col-2">
                <label htmlFor="pageCount">Page count</label>
              </div>
              <div>
                <input id="pageCount" type="text" value={this.state.pageCount || 0} onChange={this.handleChangePageCount} />
              </div>
            </div>
            <div>
              <input type="submit" value="Submit" />
            </div>
          </form>
        </div>
    );
  }
}

export default Form;
