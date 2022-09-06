import { Aeronave } from "types/aeronave";
import { useForm, Controller } from 'react-hook-form';
import { useEffect, useState } from "react";
import Select from 'react-select';
import { AxiosParams } from "types/vendor/axios";
import { BASE_URL } from "util/request";
import axios from "axios";
import { toast } from 'react-toastify';
import './styles.css';

type Props = {
    onSubmitChange: Function;
};

const Form = ({ onSubmitChange }: Props) => {
    const {
        register,
        handleSubmit,
        formState: { errors },
        setValue,
        control,
    } = useForm<Aeronave>();


    const marcas = [
        { value: 'Airbus', label: 'Airbus' },
        { value: 'Boeing', label: 'Boeing' },
        { value: 'Embraer', label: 'Embraer' }
    ]

    const vendidos = [
        { value: 'false', label: 'False' },
        { value: 'true', label: 'True' }
    ]

    const onSubmit = (formData: Aeronave) => {
        const data = {
            ...formData
        };

        const params: AxiosParams = {
            method: 'POST',
            url: `${BASE_URL}/aeronaves`,
            data
        }
        axios(params).then(response => {
            setValue('nome', '');
            setValue('marca', '');
            setValue('ano', 0);
            setValue('descricao', '');
            setValue('vendido', false);
            onSubmitChange();
            toast.info("Aeronave salva com sucesso");
        }).catch((error) => {
            toast.info("Error ao salva a  avaliação");
        });


    }


    return (
        <div className="base-card">
            <h1>Gestão de Aeronaves</h1>

            <div className="form-container">

                <form onSubmit={handleSubmit(onSubmit)}>
                    <div>
                        <div className="col-lg-6">
                            <div className="row">
                                <div className="col-sm-4">
                                    <div className="margin-bottom-30">
                                        <input
                                            {...register('nome', {
                                                required: 'Campo obrigatório',
                                            })}
                                            type="text"
                                            className={`form-control base-input ${errors.nome ? 'is-invalid' : ''
                                                }`}
                                            placeholder="Modelo"
                                            name="nome"
                                        />
                                        <div className="invalid-feedback d-block">
                                            {errors.nome?.message}
                                        </div>
                                    </div>
                                </div>

                                <div className="col-sm-4">
                                    <div className="margin-bottom-30 ">
                                        <input
                                            {...register('marca', {
                                                required: 'Campo obrigatório',
                                            })}
                                            type="text"
                                            className={`form-control base-input ${errors.marca ? 'is-invalid' : ''
                                                }`}
                                            placeholder="Marca"
                                            name="marca"
                                        />
                                        <div className="invalid-feedback d-block">
                                            {errors.marca?.message}
                                        </div>
                                    </div>
                                </div>

                                <div className="col-sm-4">
                                    <div className="margin-bottom-30 ">
                                        <input
                                            {...register('vendido', {
                                                required: 'Campo obrigatório',
                                            })}
                                            type="text"
                                            className={`form-control base-input ${errors.vendido ? 'is-invalid' : ''
                                                }`}
                                            placeholder="Vendido"
                                            name="vendido"
                                        />
                                        <div className="invalid-feedback d-block">
                                            {errors.vendido?.message}
                                        </div>
                                    </div>
                                </div>

                            </div>



                            <div className="row">
                                <div className="col-sm-6">
                                    <div className="margin-bottom-30">
                                        <input
                                            {...register('descricao', {
                                                required: 'Campo obrigatório',
                                            })}
                                            type="text"
                                            className={`form-control base-input ${errors.descricao ? 'is-invalid' : ''
                                                }`}
                                            placeholder="Descrição"
                                            name="descricao"
                                        />
                                        <div className="invalid-feedback d-block">
                                            {errors.descricao?.message}
                                        </div>
                                    </div>
                                </div>

                                <div className="col-sm-3">
                                    <div className="margin-bottom-30">
                                        <input
                                            {...register('ano', {
                                                required: 'Campo obrigatório',
                                            })}
                                            type="text"
                                            className={`form-control base-input ${errors.ano ? 'is-invalid' : ''
                                                }`}
                                            placeholder="Ano"
                                            name="ano"
                                        />
                                        <div className="invalid-feedback d-block">
                                            {errors.descricao?.message}
                                        </div>
                                    </div>
                                </div>

                                <div className="col-sm-3">
                                    <button className="btn btn-primary product-crud-button text-white">
                                        SALVAR
                                    </button>

                                </div>

                            </div>


                        </div>

                    </div>

                </form>

            </div>

        </div>
    );
}

export default Form;