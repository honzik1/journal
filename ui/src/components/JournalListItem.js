import React from 'react';
import ReactDOM from 'react-dom';
import {Link} from 'react-router';

export default class Journal extends React.Component {

  constructor(props) {
    super(props);
  }

  render() {
    let item = this.props.data;
    return (
       <Link to={`/journal/${item.id}`} className="list-group-item">
        <h3 className="list-group-item-heading">
          {item.title} <span className="label label-info">by {item.editedBy.firstName} {item.editedBy.lastName}</span>
        </h3>
          <blockquote className="list-group-item-text">
            <p>{item.discipline.title}</p>
            <footer>{item.publisher} - (*<cite title="Publishing Date">{item.publicFrom}</cite>)</footer>
          </blockquote>
      </Link>
    );
  }
}