import React from 'react';
import ReactDOM from 'react-dom';
import JournalStore from "../stores/JournalStore";
import JournalActions from "../actions/JournalActions";

export default class Journal extends React.Component {

  constructor(props) {
    super(props);
    this.mount = false;
    this.state = {
      editable: false,
      detail: null
    };
      console.log("Action: Journal.Detail-"+this.props.params.detailId);
      JournalActions.detail(this.props.params.detailId);
  }

  componentDidMount() {
      JournalStore.addChangeListener(this._onJournalChange.bind(this));
      this.mount = true;
  }

  componentWillUnmount() {
      JournalStore.removeChangeListener(this._onJournalChange.bind(this));
      this.mount = false;
  }

  _onJournalChange() {
      console.log('Journal-detail changed: ' +  JournalStore.getData('detail'));
      this.state.detail = JournalStore.getData('detail');
      if (this.mount) {
        this.setState(this.state);
      }
  }

  render() {
    let item = this.state.detail;
    if (item) {
    return (
      <form className="form-horizontal">
        <div className="form-group">
          <label htmlFor="createTime" className="col-sm-2 control-label">CreateTime</label>
          <div className="col-sm-10">
            <input type="text" className="form-control" id="createTime" value={item.createTime} />
          </div>
        </div>
        <div className="form-group">
          <label htmlFor="title" className="col-sm-2 control-label">Title</label>
          <div className="col-sm-10">
            <input type="text" className="form-control" id="title" value={item.title} />
          </div>
        </div>
        <div className="form-group">
          <label htmlFor="abbrTitle" className="col-sm-2 control-label">AbbrTitle</label>
          <div className="col-sm-10">
            <input type="text" className="form-control" id="abbrTitle" value={item.abbrTitle} />
          </div>
        </div>
        <div className="form-group">
          <label htmlFor="language" className="col-sm-2 control-label">Language</label>
          <div className="col-sm-10">
            <input type="text" className="form-control" id="language" value={item.language} />
          </div>
        </div>
        <div className="form-group">
          <label htmlFor="publisher" className="col-sm-2 control-label">Publisher</label>
          <div className="col-sm-10">
            <input type="text" className="form-control" id="publisher" value={item.publisher} />
          </div>
        </div>
        <div className="form-group">
          <label htmlFor="publicFrom" className="col-sm-2 control-label">Public From</label>
          <div className="col-sm-10">
            <input type="text" className="form-control" id="publicFrom" value={item.publicFrom} />
          </div>
        </div>
        <div className="form-group">
          <label htmlFor="frequency" className="col-sm-2 control-label">Frequency</label>
          <div className="col-sm-10">
            <input type="text" className="form-control" id="frequency" value={item.frequency} />
          </div>
        </div>
        <div className="form-group">
          <label htmlFor="impactFactor" className="col-sm-2 control-label">Impact Factor</label>
          <div className="col-sm-10">
            <input type="text" className="form-control" id="impactFactor" value={item.impactFactor} />
          </div>
        </div>
        <div className="form-group">
          <label htmlFor="impactFactorYear" className="col-sm-2 control-label">Impact Factor Year</label>
          <div className="col-sm-10">
            <input type="text" className="form-control" id="impactFactorYear" value={item.impactFactorYear} />
          </div>
        </div>
        <div className="form-group">
          <label htmlFor="issn" className="col-sm-2 control-label">Issn</label>
          <div className="col-sm-10">
            <input type="text" className="form-control" id="issn" value={item.issn} />
          </div>
        </div>
        <div className="form-group">
          <label htmlFor="lccn" className="col-sm-2 control-label">Lccn</label>
          <div className="col-sm-10">
            <input type="text" className="form-control" id="lccn" value={item.lccn} />
          </div>
        </div>
        <div className="form-group">
          <label htmlFor="coden" className="col-sm-2 control-label">Coden</label>
          <div className="col-sm-10">
            <input type="text" className="form-control" id="coden" value={item.coden} />
          </div>
        </div>
        <div className="form-group">
          <label htmlFor="oclc" className="col-sm-2 control-label">Oclc</label>
          <div className="col-sm-10">
            <input type="text" className="form-control" id="oclc" value={item.oclc} />
          </div>
        </div>
      </form>
    ); 
    } else { return <div />; }
  }
}