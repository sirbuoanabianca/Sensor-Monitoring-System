import React from 'react';
import DatePicker from "react-datepicker";
import { AxisOptions, Chart } from "react-charts";
import ResizableBox from '../../ResizableBox'


import "react-datepicker/dist/react-datepicker.css";
import { Row } from 'react-bootstrap';
import * as API_DEVICES from "../../api/device-api";
import { format } from 'date-fns'

const data = [
    {
      label: 'React Charts',
      data: [
        {
          hour: new Date(),
          consumption: 202123,
        }
      ]
    }
  ]

class ClientConsumptionChart extends React.Component {

    constructor(props) {
        super(props);
        this.toggle = this.toggle.bind(this);
        this.reloadHandler = this.props.reloadHandler;
        this.device = props.device;
        this.primaryAxis = props.primaryAxis;
        this.secondaryAxes = props.secondaryAxes;
        

        this.state = {
            data: [{data:[{hour:"",consumption:0}]}],
            startDate: new Date () ,
            errorStatus: 0,
            error: null,
        };
    }

    toggle() {
        this.setState({ collapseForm: !this.state.collapseForm });
    }

    // findRecordByHour();

    buildChartData(result){
      let data = [
        {
          label: this.device.description,
          data: [ ]
        }
      ]

      for(let i=0;i<25;i++)
        data[0].data.push({
          hour:i<10 ? '0'+ i :''+i,
          consumption:0,
        });

      result.forEach(element => {
          data[0].data[new Date(element.timestamp).getHours()].consumption += element.consumption;
      });

      return data;
    }

    fetchChartData(date) {
      return API_DEVICES.getDeviceRecordsByDay(  format(date, 'yyyy-MM-dd'),
                                                 this.device.id, (result, status, err) => {

          if (result !== null && status === 200) {
              this.setState({
                 data:this.buildChartData(result)
              });
              
              console.log(result);
          } else {
              this.setState(({
                  errorStatus: status,
                  error: err
              }));
          }
      });
  }

  selectDateHandler = (date) => {
    this.date = date;
    this.setState({startDate:date});
    this.fetchChartData(date);
  }

    render() {
        return (
            <div>
                <Row>
                  <DatePicker selected={this.state.startDate} onChange={this.selectDateHandler} name="startDate" dateFormat="yyyy-MM-dd" />
                </Row>
           
                <ResizableBox>
                <Chart
                    options={{
                        data:this.state.data,
                        primaryAxis : this.primaryAxis,
                        secondaryAxes : this.secondaryAxes,
                    }}
                />
                </ResizableBox>
                

            </div>
        );
    }
}

export default function (props) {
    const primaryAxis = React.useMemo(
        () => ({
          getValue: datum => datum.hour,
        }),
        []
      )
    
      const secondaryAxes = React.useMemo(
        () => [
          {
            getValue: datum => datum.consumption,
          },
        ],
        []
      )

    return <ClientConsumptionChart {...props} primaryAxis={primaryAxis} secondaryAxes={secondaryAxes} />;
}
