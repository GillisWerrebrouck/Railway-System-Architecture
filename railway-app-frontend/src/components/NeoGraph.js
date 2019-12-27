// credits: https://github.com/jackdbd/react-neovis-example
/* eslint-disable react/forbid-foreign-prop-types */
import React, { Component } from "react";
import ResizeAware from "react-resize-aware";
import PropTypes from "prop-types";

class NeoGraph extends Component {
  constructor(props) {
    super(props);
    this.visRef = React.createRef();
  }

  componentDidMount() {
    const { neo4jUri, neo4jUser, neo4jPassword } = this.props;
    const config = {
      container_id: this.visRef.current.id,
      server_url: neo4jUri,
      server_user: neo4jUser,
      server_password: neo4jPassword,
      labels: {
        Station: {
          caption: "name",
          size: 1.0
        },
        Route: {
          caption: "name",
          size: 1.0
        }
      },
      relationships: {
        CONNECTED_WITH: {
          caption: false,
        },
        USES_STATION: {
          caption: false,
        }
      },
      initial_cypher:
        "MATCH p=()-[]->() RETURN p",
      arrows: false
    };
    /*
      Since there is no neovis package on NPM at the moment, we have to use a "trick":
      we bind Neovis to the window object in public/index.html.js
    */
    this.vis = new window.NeoVis.default(config);
    this.vis.render();
  }

  render() {
    const { /*width,*/ height, containerId, backgroundColor } = this.props;
    return (
      <div
        id={containerId}
        ref={this.visRef}
        style={{
          width: `100%`,
          height: `${height}px`,
          backgroundColor: `${backgroundColor}`
        }}
      />
    );
  }
}

NeoGraph.defaultProps = {
  // width: 600,
  height: 600,
  backgroundColor: "#EEE"
};

NeoGraph.propTypes = {
  width: PropTypes.number.isRequired,
  height: PropTypes.number.isRequired,
  containerId: PropTypes.string.isRequired,
  neo4jUri: PropTypes.string.isRequired,
  neo4jUser: PropTypes.string.isRequired,
  neo4jPassword: PropTypes.string.isRequired,
  backgroundColor: PropTypes.string
};

class ResponsiveNeoGraph extends Component {
  state = {
    // width: 500,
    height: 500
  };

  handleResize = ({ width, height }) => {
    // const side = Math.max(width, height) / 2;
    this.setState({
      // width: side,
      height: 500
    });
  };

  render() {
    const responsiveWidth = this.state.width;
    const responsiveHeight = this.state.height;
    const neoGraphProps = {
      ...this.props,
      width: responsiveWidth,
      height: responsiveHeight
    };
    return (
      <ResizeAware
        style={{ position: "relative" }}
        onlyEvent
        onResize={this.handleResize}
      >
        <NeoGraph {...neoGraphProps} />
      </ResizeAware>
    );
  }
}

const responsiveNeoGraphDefaultProps = Object.keys(NeoGraph.defaultProps)
  .filter(d => d !== "width" && d !== "height")
  .reduce((accumulator, key) => {
    return Object.assign(accumulator, { [key]: NeoGraph.defaultProps[key] });
  }, {});

ResponsiveNeoGraph.defaultProps = {
  ...responsiveNeoGraphDefaultProps
};

const responsiveNeoGraphPropTypes = Object.keys(NeoGraph.propTypes)
  .filter(d => d !== "width" && d !== "height")
  .reduce((accumulator, key) => {
    return Object.assign(accumulator, { [key]: NeoGraph.propTypes[key] });
  }, {});

ResponsiveNeoGraph.propTypes = {
  ...responsiveNeoGraphPropTypes
};

export { NeoGraph, ResponsiveNeoGraph };