import { Marcas } from 'types/marcas';
import './styles.css';

type Props = {
    marcas: Marcas[];
}


const Marca = ({ marcas }: Props) => {
    return (
        <div className="marca-table-container base-card">
            <table className="table table-borderless">
                <thead>
                    <tr>
                        <th>Marca</th>
                    </tr>
                </thead>
                <tbody>
                    {marcas?.map(marca => {
                        return (
                            <tr>
                                <td>{marca.marca}: {marca.qto}</td>
                            </tr>
                        );
                    })}

                </tbody>
            </table>
        </div>
    );
};

export default Marca;