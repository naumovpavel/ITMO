import {useDispatch, useSelector} from "react-redux";
import {useState} from "react";
import {Slider} from "primereact/slider";
import {InputNumber} from "primereact/inputnumber";
import { Button } from 'primereact/button';

import "primereact/resources/themes/lara-light-indigo/theme.css";
import "primereact/resources/primereact.min.css";
import "primeicons/primeicons.css";

import {addHit, changeR} from "../../store/userSlice";
import {addHitRequest} from "../../service/Service";
import {showError} from "../../store/errorSlice";

import "../../resources/UserInput.css"

const UserInput = () => {
    const dispatch = useDispatch();

    const userInfo = useSelector(state => state.user);

    let x = 0, r = 1;

    const [pointX, setPointX] = useState({x:-5});

    const [point, setPoint] = useState({
        x: 0,
        y: 1,
        r: 2,
    });

    const [isLoading, setLoading] = useState(false);

    const handleRChange = (e) => {
        dispatch(changeR(parseInt(e.value)))
        setPoint({ ...point, r: parseInt(e.value) })
    };

    const handleSubmit = async () => {
        setLoading(true);

        const response = await addHitRequest(point, userInfo.token);

        if (response.message) {
            dispatch(showError({ detail: response.message }))
            return;
        }

        dispatch(addHit(response));

        setLoading(false);
    }

    return (
        <div className="controller col-md">
            <label id="controller-title">Control pane</label>

            <div className="coordinateInput">
                <label className="coordinateName">X</label>
                <div>
                    <input type="radio" name="radio" value="-5"
                           onChange={e => setPoint({ ...point, x: -5 })}
                           checked={point.x === -5}/>
                    <input type="radio" name="radio" value="-4"
                           onChange={e => setPoint({ ...point, x: -4 })} checked={point.x === -4} />
                    <input type="radio" name="radio" value="-3"
                           onChange={e => setPoint({ ...point, x: -3 })} checked={point.x === -3} />
                    <input type="radio" name="radio" value="-2"
                           checked={point.x === -2}
                           onChange={e => setPoint({ ...point, x: -2 })} />
                    <input type="radio" name="radio" value="-1"
                           checked={point.x === -1}
                           onChange={e => setPoint({ ...point, x: -1 })} />
                    <input type="radio" name="radio" value="0"
                           checked={point.x === 0}
                           onChange={e => setPoint({ ...point, x: 0 })} />
                    <input type="radio" name="radio" value="1"
                           checked={point.x === 1}
                           onChange={e => setPoint({ ...point, x: 1 })} />
                    <input type="radio" name="radio" value="2"
                           checked={point.x === 2}
                           onChange={e => setPoint({ ...point, x: 2 })} />
                    <input type="radio" name="radio" value="3"
                           onChange={e => setPoint({ ...point, x: 3 })}
                           checked={point.x === 3}/>
                </div>
                <span className="coordinateValue">{point.x}</span>
            </div>
            <div className="coordinateInput">
                <label className="coordinateName">Y</label><InputNumber
                    value={point.y} onValueChange={e => setPoint({ ...point, y: e.value })}
                    min={-3} max={3} minFractionDigits={0} maxFractionDigits={2}
                />
                <span className="coordinateValue"></span>
            </div>
            <div className="coordinateInput">
                <label className="coordinateName">R</label>
                <div>
                    <input type="radio" name="radior" value="1"
                           checked={point.r === 1}
                           onChange={e => handleRChange(e.target)} />
                    <input type="radio" name="radior" value="2"
                           checked={point.r === 2}
                           onChange={e => handleRChange(e.target)} />
                    <input type="radio" name="radior" value="3"
                           checked={point.r === 3}
                           onChange={e => handleRChange(e.target)} />
                </div>
                <span className="coordinateValue">{point.r}</span>
            </div>

            <div className="submitButton coordinateInput">
                <Button label="Check" icon="pi pi-times-circle" iconPos="right" loading={isLoading} onClick={handleSubmit} />
            </div>
            {/* <p>Current data. X: {point.x}, Y: {point.y}, R: {point.r}</p> */}
        </div>
    );
}

export default UserInput;