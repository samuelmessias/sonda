import './styles.css';

type Props = {
    qto: number;
}


const Summary = ({ qto }: Props) => {
    return (
        <div className="summary-container base-card">
            <div className="row">
                <div className="col-sm-6">
                    <span>Década 90:</span> 2 Aeronaves
                </div>
                <div className="col-sm-6">
                    <span>Essa semana:</span> {qto} Aeronaves
                </div>
            </div>
        </div>
    );
};

export default Summary;