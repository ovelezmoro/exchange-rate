import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthServiceService } from './services/auth-service.service';
import { ExchangeRateService } from './services/exchange-rate.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit, AfterViewInit {
  @ViewChild('mymodal', { static: true }) modal: any;
  
  modalReference: any;
  isAuthenticate: boolean = false;
  baseCurrency: string = '';
  exchangeCurrenty: string = '';
  exchangeRateResponse: any;
  amout!: number;

  login: {username: string, password: string} = {
    username: '',
    password: ''
  }

  constructor(private modalService: NgbModal, private authService: AuthServiceService,
    private exchangeService: ExchangeRateService) {}

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    //TODO: Validar el token ingresado de lo contrario
    if(!this.authService.isAuthenticate()) {
      this.modalReference = this.modalService.open(this.modal, {
        ariaLabelledBy: 'modal-basic-title',
        keyboard: false,
        backdrop: 'static',
      });
    }
  }

  changeInputs() {
    const baseCurrencyChange = this.exchangeCurrenty
    const exchangeCurrentyChange = this.baseCurrency
    this.baseCurrency = baseCurrencyChange
    this.exchangeCurrenty = exchangeCurrentyChange
    this.exchangeRateResponse = null;
  }

  logout() {
    this.authService.logout();
    this.modalReference = this.modalService.open(this.modal, {
      ariaLabelledBy: 'modal-basic-title',
      keyboard: false,
      backdrop: 'static',
    });
  }

  authentication() {
    this.authService.login(this.login.username, this.login.password)
      .subscribe((response) => {
        console.log(response);
        this.authService.setToken(response.token)
        this.modalReference.close();
      });
  }
  
  convert(){
    this.exchangeService.convert(this.baseCurrency, this.exchangeCurrenty, this.amout)
    .subscribe((response) => {
      console.log(response);
      console.log(response.result['PEN']);
      this.exchangeRateResponse = {}
      this.exchangeRateResponse.amount = response.result[`${this.exchangeCurrenty}`]
      this.exchangeRateResponse.money = this.exchangeCurrenty
      this.exchangeRateResponse.rate = response.result.rate
      console.log(this.exchangeRateResponse);

    });
  }
}
