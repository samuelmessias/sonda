import axios from 'axios';
import Form from 'components/Form';
import Marca from 'components/Marca';
import Summary from 'components/Summary';
import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { Aeronave } from 'types/aeronave';
import { Marcas } from 'types/marcas';
import { AxiosParams } from 'types/vendor/axios';
import { SpringPage } from 'types/vendor/spring';
import { BASE_URL } from 'util/request';
import { ReactComponent as SetaImage } from 'assets/img/seta.svg';
import './styles.css';

type FormData = {
    modelo: string;
};

const Home = () => {
    const [page, setPage] = useState<SpringPage<Aeronave>>();

    const [marcas, setMarcas] = useState<Marcas[]>([]);

    const [qtoSemana, setQtoSemana] = useState<number>(0);

    const [formData, setFormData] = useState<FormData>({
        modelo: '',
    });

    const { register, handleSubmit } = useForm();

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const name = event.target.name;
        const value = event.target.value;

        setFormData({ ...formData, [name]: value })
    };

    const handlerSubmitPesquisa = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const params: AxiosParams = {
            method: 'GET',
            url: `${BASE_URL}/aeronaves/find?nome=${formData.modelo}`
        }
        axios(params).then(response => {
            setPage(response.data);
        })

    };

    const handleDelete = (aeronaveId: number) => {
        if (!window.confirm("Tem certeza que deseja deletar?")) {
            return;
        }

        const params: AxiosParams = {
            method: 'DELETE',
            url: `${BASE_URL}/aeronaves/${aeronaveId}`
        }

        axios(params).then(response => {
            getAeronaves();

        })


    }

    const getAeronaves = () => {
        const params: AxiosParams = {
            method: 'GET',
            url: `${BASE_URL}/aeronaves`,
            params: {
                page: 0,
                size: 20
            }
        }
        axios(params).then(response => {
            setPage(response.data);
        })
    }

    useEffect(() => {
        const params: AxiosParams = {
            method: 'GET',
            url: `${BASE_URL}/aeronaves`,
            params: {
                page: 0,
                size: 20
            }
        }
        axios(params).then(response => {
            setPage(response.data);
            console.log(page);
        })

    }, []);

    useEffect(() => {
        const params: AxiosParams = {
            method: 'GET',
            url: `${BASE_URL}/aeronaves/qto/marcas`
        }
        axios(params).then(response => {
            setMarcas(response.data);
        })

    }, [page]);

    useEffect(() => {
        const params: AxiosParams = {
            method: 'GET',
            url: `${BASE_URL}/aeronaves/semana`
        }
        axios(params).then(response => {
            setQtoSemana(response.data);
        })

    }, [page]);



    return (
        <>

            <Form onSubmitChange={getAeronaves} />
            <Summary qto={qtoSemana} />
            <div className="aero-table-container base-card">

                <form onSubmit={handlerSubmitPesquisa}>
                    <div className="card-input">
                        <input
                            name="modelo"
                            value={formData.modelo}
                            type="text"
                            placeholder="Pesquisa por Modelo"
                            onChange={handleChange}
                        />
                    </div>
                </form>

                <table className="table table-bordered">
                    <thead className="thead-dark">
                        <tr>
                            <th>ID</th>
                            <th>Marca</th>
                            <th>Modelo</th>
                            <th>Descrição</th>
                            <th>Ano</th>
                            <th>Vendido</th>
                            <th>Excluir</th>
                        </tr>
                    </thead>
                    <tbody>

                        {page?.content.map(aeronave => {
                            return (
                                <tr key={aeronave.id}>
                                    <td>{aeronave.id}</td>
                                    <td>{aeronave.marca}</td>
                                    <td>{aeronave.nome}</td>
                                    <td>{aeronave.descricao}</td>
                                    <td>{aeronave.ano}</td>
                                    <td>{String(aeronave.vendido)} </td>
                                    <td><button onClick={() => handleDelete(aeronave.id)} className="btn-delete">Excluir</button></td>
                                </tr>
                            );
                        })}

                    </tbody>
                </table>
            </div>
            <Marca marcas={marcas} />
        </>
    );
};

export default Home;