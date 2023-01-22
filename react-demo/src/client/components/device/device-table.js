import React from "react";
import Table from "../../commons/tables/table";


const columns = [
    {
        Header: 'Description',
        accessor: 'description',
    },


    {
        Header: 'Address',
        accessor: 'address',
    }
];

const filters = [
    {
        accessor: 'users',
    }
];

class ClientTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            tableData: this.props.tableData
        };
    }

    render() {
        return (
            <Table
                data={this.state.tableData}
                columns={columns}
                search={filters}
                pageSize={10}
            />
        )
    }
}

export default ClientTable;