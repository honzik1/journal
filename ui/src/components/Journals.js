import React from 'react';
import ReactDOM from 'react-dom';
import JournalStore from "../stores/JournalStore";
import JournalListItem from "./JournalListItem";

export default class Journals extends React.Component {

  constructor(props) {
    super(props);
    this.state = {};
  }

  componentDidMount() {
      JournalStore.addChangeListener(this._onJournalsChange.bind(this));
      this.mount = true;
  }

  /**
   * Will unmount
   * @public
   */
  componentWillUnmount() {
    JournalStore.removeChangeListener(this._onJournalsChange.bind(this));
    this.mount = false;
  }

  _onJournalsChange() {
      this.state.data = JournalStore.getData('data');
      if (this.mount) {
        this.setState(this.state);
      }
  }


  render() {
    return (
      <div className="panel panel-primary">
            {
              (!this.state.data) 
                ? 'No data loaded.' 
                : this.state.data.map(item => 
                  <JournalListItem key={item.id} data={item} /> 
                  )
            }
      </div>
    );
  }
}